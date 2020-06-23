package com.example.workmgr

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.workmgr.worker.NotificationWorker
import kotlinx.android.synthetic.main.activity_main.*

const val MESSAGE="STATUS"
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // create WorkManager object to manage , enque requests
        val workerManager = WorkManager.getInstance()

        // create OneTimeWorkRequest object bcoz we want to execute request one time
        val oneTimeWorkReq = OneTimeWorkRequest.Builder(NotificationWorker::class.java).build()

        // enque the request with WorkerManager
        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                workerManager.enqueue(oneTimeWorkReq)
            }
        })

        workerManager.getWorkInfoByIdLiveData(oneTimeWorkReq.id).observe(this, object :Observer<WorkInfo>{
            override fun onChanged(workInfo: WorkInfo?) {
                if (workInfo==null)return
                val state = workInfo.state
                textView.setText(state.toString()+"\n")

            }

        })
    }
}