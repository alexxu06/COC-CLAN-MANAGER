package com.example.cocapi.services;

import com.example.cocapi.models.Player;
import com.example.cocapi.models.war.War;
import com.example.cocapi.proxies.WarProxy;
import com.example.cocapi.repository.WarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class WarServiceTest {
    @Mock
    private WarRepository warRepository;

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
