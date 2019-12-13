package com.zy.ypro.http.multipart

interface OnProgressListener {
    /**
     * Invoked when progress has been changed
     *
     * @param current Current finished progress
     * @param total   Total progress, NOTE total may be 0 if total is not available
     * @param sender  Sender
     * @return Returns true to terminate processing, false otherwise
     */
    fun onProgress(current: Int, total: Int, sender: Any): Boolean
}
