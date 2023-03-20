package br.com.ada.cardgame.services;

import br.com.ada.cardgame.services.dtos.MatchAnalyticsDto;
import br.com.ada.cardgame.services.dtos.MatchCurrentRoundDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface MatchService {

    MatchCurrentRoundDto createMatch(String username);

    List<MatchAnalyticsDto> findByUsername(String username);

    MatchCurrentRoundDto findById(Long id);

    void stopMatch(Long id);

    void validateOwnership(String username, Long id);
}
