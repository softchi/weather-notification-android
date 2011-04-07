/*
 *  Android Weather Notification.
 *  Copyright (C) 2010  Denis Nelubin aka Gelin
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 *  http://gelin.ru
 *  mailto:den@gelin.ru
 */

package ru.gelin.android.weather;

import static org.junit.Assert.*;

import org.junit.Test;

public class SimpleTemperatureTest {
    
    @Test
    public void testConstructor() {
        SimpleTemperature temp1 = new SimpleTemperature(UnitSystem.SI);
        assertEquals(UnitSystem.SI, temp1.getUnitSystem());
        SimpleTemperature temp2 = new SimpleTemperature(UnitSystem.US);
        assertEquals(UnitSystem.US, temp2.getUnitSystem());
    }
    
    @Test
    public void testSetLowHigh() {
        SimpleTemperature temp = new SimpleTemperature(UnitSystem.SI);
        temp.setCurrent(25, UnitSystem.SI);
        assertEquals(25, temp.getLow());
        assertEquals(25, temp.getHigh());
    }
    
    @Test
    public void testSetLowCurrent() {
        SimpleTemperature temp = new SimpleTemperature(UnitSystem.SI);
        temp.setHigh(25, UnitSystem.SI);
        assertEquals(25, temp.getLow());
        assertEquals(25, temp.getHigh());
        assertEquals(25, temp.getCurrent());
    }
    
    @Test
    public void testSetHighCurrent() {
        SimpleTemperature temp = new SimpleTemperature(UnitSystem.SI);
        temp.setLow(25, UnitSystem.SI);
        assertEquals(25, temp.getLow());
        assertEquals(25, temp.getHigh());
        assertEquals(25, temp.getCurrent());
    }
    
    @Test
    public void testSetCurrent() {
        SimpleTemperature temp = new SimpleTemperature(UnitSystem.SI);
        temp.setLow(25, UnitSystem.SI);
        temp.setHigh(35, UnitSystem.SI);
        assertEquals(25, temp.getLow());
        assertEquals(35, temp.getHigh());
        assertEquals(30, temp.getCurrent());
    }
    
    @Test
    public void testSetCurrent2() {
        SimpleTemperature temp = new SimpleTemperature(UnitSystem.SI);
        temp.setLow(25, UnitSystem.SI);
        temp.setHigh(28, UnitSystem.SI);
        assertEquals(25, temp.getLow());
        assertEquals(28, temp.getHigh());
        assertEquals(27, temp.getCurrent());
    }
    
    @Test
    public void testSetAll() {
        SimpleTemperature temp = new SimpleTemperature(UnitSystem.SI);
        temp.setLow(25, UnitSystem.SI);
        temp.setHigh(35, UnitSystem.SI);
        temp.setCurrent(32, UnitSystem.SI);
        assertEquals(25, temp.getLow());
        assertEquals(35, temp.getHigh());
        assertEquals(32, temp.getCurrent());
    }
    
    @Test
    public void testConvertSI2US() {
        SimpleTemperature temp = new SimpleTemperature(UnitSystem.US);
        temp.setLow(25, UnitSystem.SI);
        temp.setHigh(35, UnitSystem.SI);
        temp.setCurrent(32, UnitSystem.SI);
        assertEquals(77, temp.getLow());
        assertEquals(95, temp.getHigh());
        assertEquals(90, temp.getCurrent());
    }
    
    @Test
    public void testConvertUS2SI() {
        SimpleTemperature temp = new SimpleTemperature(UnitSystem.SI);
        temp.setLow(77, UnitSystem.US);
        temp.setHigh(95, UnitSystem.US);
        temp.setCurrent(90, UnitSystem.US);
        assertEquals(25, temp.getLow());
        assertEquals(35, temp.getHigh());
        assertEquals(32, temp.getCurrent());
    }
    
    @Test
    public void testConvert() {
        SimpleTemperature temp1 = new SimpleTemperature(UnitSystem.SI);
        temp1.setLow(25, UnitSystem.SI);
        temp1.setHigh(35, UnitSystem.SI);
        temp1.setCurrent(32, UnitSystem.SI);
        SimpleTemperature temp2 = temp1.convert(UnitSystem.US);
        assertEquals(UnitSystem.US, temp2.getUnitSystem());
        assertEquals(77, temp2.getLow());
        assertEquals(95, temp2.getHigh());
        assertEquals(90, temp2.getCurrent());
    }

}