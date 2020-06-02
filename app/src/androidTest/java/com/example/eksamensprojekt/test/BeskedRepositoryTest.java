package com.example.eksamensprojekt.test;


import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;


public class BeskedRepositoryTest {




    @Test
    public void sendNyBesked() {
        FalskBesked besked = new FalskBesked();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("afsender", besked.getAfsender());
        hashMap.put("besked", besked.getBesked());
        hashMap.put("modtager", besked.getModtager());

        HashMap<String, Object> testHashMap  = new HashMap<>();
        testHashMap.put("afsender", "123123");
        testHashMap.put("besked", "yeah test");
        testHashMap.put("modtager", "321321");

        assertEquals(testHashMap,hashMap);

    }


    private class FalskBesked {
        private String afsender = "123123";
        private String modtager = "321321";
        private String besked = "yeah test";

        public FalskBesked(String afsender, String modtager, String besked) {
            this.afsender = afsender;
            this.modtager = modtager;
            this.besked = besked;
        }

        public FalskBesked() {
        }

        public String getAfsender() {
            return afsender;
        }

        public void setAfsender(String afsender) {
            this.afsender = afsender;
        }

        public String getModtager() {
            return modtager;
        }

        public void setModtager(String modtager) {
            this.modtager = modtager;
        }

        public String getBesked() {
            return besked;
        }

        public void setBesked(String besked) {
            this.besked = besked;
        }
    }

}