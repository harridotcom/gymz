import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.gym.pages.parseToLocalDate
import com.example.gymz.objs.Client
import com.example.gymz.vms.ClientViewModel
import java.time.LocalDate

class MyWorker(
    private val context: Context,
    workerParameters: WorkerParameters
//    clientViewModel: ClientViewModel
) : Worker(context, workerParameters) {

    companion object {
        private const val CHANNEL_ID = "worker_notifications"
        private const val NOTIFICATION_ID = 1
    }

    override fun doWork(): Result {
        showNotification("Membership Expired Members", "Hey! List of Members whose membership is expired today is available")
        return Result.success()
    }

//    @RequiresApi(Build.VERSION_CODES.O)
//    fun getExpiredMembers(clientViewModel: ClientViewModel){
//        val list = clientViewModel.clientsList
//        var final_list = mutableListOf<Client>()
//        list.value?.forEach {
//            client ->
//            if (parseToLocalDate(client.endDate)?.isEqual(LocalDate.now()) == true){
//
//            }
//        }
//    }

    private fun showNotification(title: String, message: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create notification channel for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Worker Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifications from Worker class"
            }
            notificationManager.createNotificationChannel(channel)
        }

        // Build the notification
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        // Show the notification
        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}