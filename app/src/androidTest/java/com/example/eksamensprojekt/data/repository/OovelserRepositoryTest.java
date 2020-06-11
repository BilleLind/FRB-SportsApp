package com.example.eksamensprojekt.data.repository;

import junit.framework.TestCase;
import org.junit.Test;

public class OovelserRepositoryTest extends TestCase {

    private static OovelserRepository oovelserRepository;

    @Test
    public void testGetInstance() {
        Object instanceTest = oovelserRepository.getInstance();
        assertNotNull(instanceTest);
    }
}