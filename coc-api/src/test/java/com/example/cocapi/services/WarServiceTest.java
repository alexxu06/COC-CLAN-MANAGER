package com.example.cocapi.services;

import com.example.cocapi.proxies.WarProxy;
import com.example.cocapi.repository.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.verify;

@SpringBootTest
public class WarServiceTest {
    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private WarService warService;

    @Mock
    private WarProxy warProxy;

    @Mock
    private DateConvertService dateConvertService;

    @Test
    public void testDailyUpdate() {

    }


}
