package de.demokratie.polizeibot.utils;

import de.demokratie.polizeibot.objects.SimpleMute;
import de.demokratie.polizeibot.objects.SimpleWarn;
import de.demokratie.polizeibot.objects.SimpleWarnList;
import de.demokratie.polizeibot.objects.Warn;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConfigUtilsTest {

    @Test
    void serialize() {
        //Test for warns
        SimpleWarn warn = new SimpleWarn();
        warn.setDate(123L);
        warn.setReason("TEST REASON");
        warn.setWarner("TEST WARNER");
        SimpleWarn warn2 = new SimpleWarn();
        warn2.setDate(456L);
        warn2.setReason("2TEST REASON");
        warn2.setWarner("2TEST WARNER");
        SimpleWarnList warnList = new SimpleWarnList();
        List<SimpleWarn> warns = new ArrayList<>();
        warns.add(warn);
        warns.add(warn2);
        warnList.setWarns(warns);
        try {
            ConfigUtils.serialize(warnList, "users/TestID-1/warns.yml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Test for mute
        SimpleMute mute = new SimpleMute();
        mute.setDate(123L);
        mute.setExpireDate(456L);
        mute.setMember("TEST MEMBER");
        mute.setMuted(true);
        mute.setMuter("TEST MUTER");
        mute.setPermanent(false);
        mute.setType("TEST TYPE");
        try {
            ConfigUtils.serialize(mute, "users/TestID-1/mutes.yml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void deserialize() {
        //Warns test
        SimpleWarnList list = null;
        try {
            list = (SimpleWarnList) ConfigUtils.deserialize("users/TESTID-1/warns.yml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assertNotEquals(null, list);
        if (list != null) {
            for(SimpleWarn warn : list.getWarns()) {
                System.out.println("-Date " + warn.getDate() + " -Reason " + warn.getReason() + " -Warner " + warn.getWarner());
            }
        }

        //Mute test
        SimpleMute mute = null;
        try {
            mute = (SimpleMute) ConfigUtils.deserialize("users/TESTID-1/mutes.yml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assertNotEquals(null, mute);
        if (mute != null) {
            assertEquals(123L, mute.getDate());
            assertEquals("TEST MEMBER", mute.getMember());
            assertEquals(456L, mute.getExpireDate());
            assertEquals("TEST MUTER", mute.getMuter());
            assertTrue(mute.getMuted());
            assertFalse(mute.getPermanent());
        }
    }
}