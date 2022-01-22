package org.test.piano.service;

import org.springframework.stereotype.Service;

@Service
public class SomeServiceImpl implements SomeService{

    @Override
    public void test() {
        System.out.println("test test test");
    }
}
