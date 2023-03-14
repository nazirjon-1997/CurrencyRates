package com.developer.valyutaapp.service.auto

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.developer.valyutaapp.R
import com.developer.valyutaapp.core.common.PATH_EXP
import com.developer.valyutaapp.domain.repository.ValuteRemoteRepository
import com.developer.valyutaapp.ui.MainActivity
import com.developer.valyutaapp.utils.Notification
import com.developer.valyutaapp.utils.Utils
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class AutoUpdateWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams), KoinComponent {

    private val valuteRemoteRepository: ValuteRemoteRepository by inject()

    companion object {
        const val WORK_RESULT = "work_result"
    }

    override suspend fun doWork(): Result {
        valuteRemoteRepository.getAllValutes(Utils.getDate(), PATH_EXP)
        val taskData = inputData
        val taskDataString = taskData.getString(MainActivity.MESSAGE_STATUS)
        Notification.showNotification(
            applicationContext,
            applicationContext.getString(R.string.app_name),
            taskDataString.toString()
        )
        val outputData = Data.Builder().putString(WORK_RESULT, "Finished").build()
        return Result.success(outputData)
    }
}