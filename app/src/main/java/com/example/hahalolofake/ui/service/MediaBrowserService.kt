package com.example.hahalolofake.ui.service

import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.media.MediaBrowserServiceCompat

class MediaBrowserService : MediaBrowserServiceCompat() {

    private lateinit var mediaSession: MediaSessionCompat

    override fun onCreate() {
        super.onCreate()

        Log.d("ThangND", "MediaBrowserService: onCreate")

        // Khởi tạo và cấu hình MediaSessionCompat
        mediaSession = MediaSessionCompat(this, "MyMediaSession")
        sessionToken = mediaSession.sessionToken

        val metadata = MediaMetadataCompat.Builder()
            .putString(MediaMetadataCompat.METADATA_KEY_TITLE, "Tên bài hát")
            .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, "Nghệ sĩ")
            .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, "Album")
            // Đặt các thông tin metadata khác (nếu cần)
            .build()

        val playbackState = PlaybackStateCompat.Builder()
            .setState(PlaybackStateCompat.STATE_SKIPPING_TO_NEXT, 0, 1.0f)
            .setBufferedPosition(10000) // Ví dụ: thời gian hiện tại của bài hát
            .setActions(
                PlaybackStateCompat.ACTION_PLAY or
                        PlaybackStateCompat.ACTION_PAUSE or
                        PlaybackStateCompat.ACTION_SKIP_TO_NEXT or
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
            )
            .build()

        // Định nghĩa các trạng thái và cài đặt bài hát đang phát
        mediaSession.setMetadata(metadata)
        mediaSession.setPlaybackState(playbackState)

        // Thiết lập MediaSessionConnector để kết nối với MediaBrowserServiceCompat
        mediaSession.setCallback(mediaSessionCallback)

        // Bắt đầu MediaSession
        mediaSession.isActive = true
    }

    private val mediaSessionCallback = object : MediaSessionCompat.Callback() {

    }

    override fun onGetRoot(clientPackageName: String, clientUid: Int, rootHints: Bundle?): BrowserRoot? {
        if (isClientAllowed(clientPackageName, clientUid)) {
            // Trả về một BrowserRoot hợp lệ cho ứng dụng khách
            return BrowserRoot("my_media_root", null)
        } else {
            // Trả về null hoặc BrowserRoot không hợp lệ để từ chối quyền truy cập
            return null
        }
    }

    private fun isClientAllowed(clientPackageName: String, clientUid: Int): Boolean {
        // Danh sách các gói ứng dụng được phép truy cập
        val allowedPackages = listOf("com.spotify.music", "com.zing.mp3")

        // Kiểm tra xem clientPackageName có trong danh sách gói ứng dụng được phép
        if (allowedPackages.contains(clientPackageName)) {
            // Kiểm tra các quyền hoặc điều kiện bổ sung (nếu cần)
            return true
        }

        // Nếu không được phép hoặc không thỏa mãn điều kiện nào đó, trả về false
        return false
    }

    override fun onLoadChildren(parentId: String, result: Result<List<MediaBrowserCompat.MediaItem>>) {
        // Truyền danh sách các MediaItem đại diện cho bài hát đang phát ở đây
        val mediaItems = mutableListOf<MediaBrowserCompat.MediaItem>()

        // Thêm các MediaItem vào danh sách ở đây

        result.sendResult(mediaItems)
    }

    override fun onDestroy() {
        super.onDestroy()

        // Dừng và giải phóng tài nguyên khi dịch vụ bị hủy
        mediaSession.isActive = false
        mediaSession.release()
    }
}