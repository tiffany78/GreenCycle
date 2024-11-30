package com.manpro.greencycle.User.HistorySetoran;
import java.time.LocalDate;
import java.util.List;


public interface HistorySampahRepository {
    List<HistorySampah> findSetoranHistory(String filter, LocalDate startDate, LocalDate endDate);
    List<HistorySampah> filterSampah(String username,String filter);
}