package br.com.raizesdonordeste.api_raizes_do_nordeste.service;

import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request.UnitRequestDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.PageResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.UnitResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.Stock;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.Unit;
import br.com.raizesdonordeste.api_raizes_do_nordeste.repository.StockRepository;
import br.com.raizesdonordeste.api_raizes_do_nordeste.repository.UnitRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UnitService {

    private final UnitRepository unitRepository;
    private final StockRepository stockRepository;

    public UnitResponseDTO createUnitAndStock(UnitRequestDTO dto) {
        Unit unit = new Unit(dto.name(), dto.city(), dto.state());
        Unit savedUnit = unitRepository.save(unit);

        Stock stock = new Stock(savedUnit);
        Stock savedStock = stockRepository.save(stock);

        savedUnit.setStock(savedStock);

        //log para auditoria
        log.info("Unidade e estoque criados | id={} | nome={} | cidade={} | estado={} | id_estoque={}",
                savedUnit.getId(), savedUnit.getName(), savedUnit.getCity(), savedUnit.getState(), stock.getId());

        return mapToResponseDTO(savedUnit);
    }

    public UnitResponseDTO findById (Long id) {
        Unit unit = unitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada"));
        return mapToResponseDTO(unit);
    }

    public PageResponseDTO<UnitResponseDTO> findAllActiveUnits(Pageable pageable) {
        return PageResponseDTO.of(unitRepository.findAllByActiveTrue(pageable)
                .map(this::mapToResponseDTO));
    }

    public void deactivate(Long id) {
        Unit unit = unitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada"));
        unit.setActive(false);
        unitRepository.save(unit);

        //log para auditoria
        log.info("Unidade desativada | id={} | nome={} | cidade={} | estado={}",
                unit.getId(), unit.getName(), unit.getCity(), unit.getState());
    }

    private UnitResponseDTO mapToResponseDTO(Unit unit) {
        return new UnitResponseDTO(
                unit.getId(),
                unit.getName(),
                unit.getCity(),
                unit.getState(),
                unit.isActive(),
                unit.getStock().getId()
        );
    }


}
