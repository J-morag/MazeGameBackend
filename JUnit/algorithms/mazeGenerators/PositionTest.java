package algorithms.mazeGenerators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JUnitTestingPositionTest {

    Position pos = new Position(3,10);
    Position pos1 = new Position(-1,-1);

    @Test
    void getRowIndex() {
        assertEquals(3,pos.getRowIndex());
        assertEquals(-1,pos1.getRowIndex());
    }

    @Test
    void getColumnIndex() {
        assertEquals(10,pos.getColumnIndex());
        assertEquals(-1,pos1.getColumnIndex());
    }
//
//    @Test
//    void toString() {
//    }

//    @Test
//    void equals() {
//    }
}