package br.com.raizesdonordeste.api_raizes_do_nordeste.service;

import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request.UnitRequestDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.PageResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.UnitResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.Unit;
import br.com.raizesdonordeste.api_raizes_do_nordeste.repository.UnitRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UnitService {

    private final UnitRepository unitRepository;

    public UnitResponseDTO create(UnitRequestDTO dto) {
        Unit unit = new Unit(dto.name(), dto.city(), dto.state());
        Unit savedUnit = unitRepository.save(unit);

        return mapToResponseDTO(savedUnit);
    }

    public UnitResponseDTO findById (Long id) {
        Unit unit = unitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada"));
        return mapToResponseDTO(unit);
    }

    public PageResponseDTO<UnitResponseDTO> findAll(Pageable pageable) {
        return PageResponseDTO.of(unitRepository.findAll(pageable)
                .map(this::mapToResponseDTO));
    }

    public void deactivate(Long id) {
        Unit unit = unitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada"));
        unit.setActive(false);
        unitRepository.save(unit);
    }

    private UnitResponseDTO mapToResponseDTO(Unit unit) {
        return new UnitResponseDTO(
                unit.getId(),
                unit.getName(),
                unit.getCity(),
                unit.getState(),
                unit.isActive()
        );
    }


}
