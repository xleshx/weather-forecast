package company.challenge;

import company.challenge.dto.TransactionDTO;
import org.junit.Before;
import org.junit.Test;

import java.time.Clock;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.Instant.parse;
import static java.time.ZoneOffset.UTC;
import static java.util.stream.LongStream.rangeClosed;

public class StatisticsServiceTest {

    private OpenWeatherService service;


    private Clock clock;

    private double DELTA = 0.01;

    @Before
    public void setup() {
        clock = Clock.fixed(parse("2018-03-18T12:00:00Z"), UTC);
//        cache = new ConcurrentHashMap<>(60);
//        service = new OpenWeatherService(clock, openWeatherMapClient);
    }

    @Test
    public void testCacheUpdate10BucketsWithDifferentAmount() {
        List<TransactionDTO> transactions = rangeClosed(1, 10)
                .map(v -> clock.instant().toEpochMilli() - v * 500)
                .mapToObj(ts -> getTransactionDTO(ts, 500))
                .collect(Collectors.toList());

        TransactionDTO transactionMin = getTransactionDTO(
                clock.instant().minusMillis(999).toEpochMilli(), 20);

        TransactionDTO transactionMax = getTransactionDTO(
                clock.instant().minusMillis(998).toEpochMilli(), 2000);

        // when
//        transactions.forEach(t -> service.save(t));
//        service.save(transactionMin);
//        service.save(transactionMax);

        // then
//        ForecastDTO statistics = service.getStatistics();

//        assertEquals(5, cache.size());
//        assertEquals(7020.0, statistics.getSum(), DELTA);
//        assertEquals(585.0, statistics.getAvg(), DELTA);
//        assertEquals(20.0, statistics.getMin(), DELTA);
//        assertEquals(2000.0, statistics.getMax(), DELTA);
//        assertEquals(12, statistics.getCount());
    }

    private TransactionDTO getTransactionDTO(long oneSecOld, double amount) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(amount);
        transactionDTO.setTimestamp(oneSecOld);
        return transactionDTO;
    }


}