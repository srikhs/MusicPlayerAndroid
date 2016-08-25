// MusicInterface.aidl
package com.example.saisrikanth.common;

// Declare any non-default types here with import statements

interface MusicInterface {
   void playMusic(long n);
    void stopMusic();
    void pauseMusic();
        void resumeMusic();
        String[] TransactionHistory();
}
