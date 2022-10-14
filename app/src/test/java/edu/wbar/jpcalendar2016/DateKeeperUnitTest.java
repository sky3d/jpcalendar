package edu.wbar.jpcalendar2016;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;


public class DateKeeperUnitTest {
    private final Calendar cal = Calendar.getInstance();
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.YYYY");
    private DateKeeper mDK;

    private int currentYear = cal.get(Calendar.YEAR);

    @Before
    public void setUp() {
        mDK = new DateKeeper();
        cal.setTime(new Date());
    }

      @After
    public void tearDown() {
        mDK = null;
    }

    private String _format(Date dt) {
        return sdf.format(dt);
    }

    @Test
    public void current() throws Exception {
        int m = cal.get(Calendar.MONTH);
        int d = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(mDK.current());

        assertEquals(currentYear, cal.get(Calendar.YEAR));
        assertEquals(m, cal.get(Calendar.MONTH));
        assertEquals(d, cal.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void getKey() {
        mDK.set(10, 20);
        assertEquals("20161120", mDK.getKey());
    }

    @Test
    public void formatCurrent() {
        mDK.set(10, 20);
        assertTrue(mDK.formatCurrent().startsWith("20 November"));
    }

    @Test
    public void random() throws Exception {
        Date current = mDK.current();

        mDK.random();

        assertNotEquals(_format(current),_format(mDK.current()));
    }

    @Test
    public void next() throws Exception {
        mDK.set(10, 20);
        mDK.next();

        cal.setTime(mDK.current());

        assertEquals(currentYear, cal.get(Calendar.YEAR));
        assertEquals(10, cal.get(Calendar.MONTH));
        assertEquals(21, cal.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void next_month() throws Exception {
        mDK.set(4, 31);
        mDK.next();

        cal.setTime(mDK.current());

        assertEquals(currentYear, cal.get(Calendar.YEAR));
        assertEquals(5, cal.get(Calendar.MONTH));
        assertEquals(1, cal.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void next_year() throws Exception {
        mDK.set(11, 31);
        mDK.next();
        cal.setTime(mDK.current());

        assertEquals(currentYear + 1, cal.get(Calendar.YEAR));
        assertEquals(0, cal.get(Calendar.MONTH));
        assertEquals(1, cal.get(Calendar.DAY_OF_MONTH));

        assertEquals("20160101", mDK.getKey());
    }

    @Test
    public void prev() throws Exception {
        mDK.set(10, 20);
        mDK.prev();

        cal.setTime(mDK.current());

        assertEquals(currentYear, cal.get(Calendar.YEAR));
        assertEquals(10, cal.get(Calendar.MONTH));
        assertEquals(19, cal.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void prev_month() throws Exception {
        mDK.set(5, 1);
        mDK.prev();

        cal.setTime(mDK.current());

        assertEquals(currentYear, cal.get(Calendar.YEAR));
        assertEquals(4, cal.get(Calendar.MONTH));
        assertEquals(31, cal.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void prev_year() throws Exception {
        mDK.set(0, 1);
        mDK.prev();

        cal.setTime(mDK.current());

        assertEquals(currentYear - 1, cal.get(Calendar.YEAR));
        assertEquals(11, cal.get(Calendar.MONTH));
        assertEquals(31, cal.get(Calendar.DAY_OF_MONTH));

        assertEquals("20161231", mDK.getKey());
    }
    @Test
    public void set() throws Exception {
        mDK.set(11, 30);
        cal.setTime(mDK.current());

        assertEquals(currentYear, cal.get(Calendar.YEAR));
        assertEquals(11, cal.get(Calendar.MONTH));
        assertEquals(30, cal.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void set_year() throws Exception {
        mDK.set(11, 31);
        mDK.next();
        mDK.next();
        cal.setTime(mDK.current());

        assertEquals(currentYear + 1, cal.get(Calendar.YEAR));

        mDK.set(10, 20);
        cal.setTime(mDK.current());

        assertEquals(currentYear + 1, cal.get(Calendar.YEAR));
        assertEquals(10, cal.get(Calendar.MONTH));
        assertEquals(20, cal.get(Calendar.DAY_OF_MONTH));
    }
}