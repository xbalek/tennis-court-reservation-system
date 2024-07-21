package com.xbalek.tennis_court_reservation_system.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xbalek.tennis_court_reservation_system.dto.SurfaceTypeDTO;
import com.xbalek.tennis_court_reservation_system.exceptions.NotFoundException;
import com.xbalek.tennis_court_reservation_system.mappers.SurfaceTypeMapper;
import com.xbalek.tennis_court_reservation_system.model.SurfaceType;
import com.xbalek.tennis_court_reservation_system.repository.interfaces.SurfaceTypeRepositoryInterface;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Repository
public class SurfaceTypeRepository implements SurfaceTypeRepositoryInterface {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private SurfaceTypeMapper surfaceTypeMapper;

    @Override
    public SurfaceTypeDTO findById(Long id) {
        SurfaceType foundSurface = entityManager.find(SurfaceType.class, id);
        if (foundSurface == null || foundSurface.getIsDeleted()) {
            throw new NotFoundException("Surface type not found");
        }
        return surfaceTypeMapper.toDTO(foundSurface);
    }

    @Override
    @Transactional
    public SurfaceTypeDTO create(SurfaceType surfaceType) {
        entityManager.persist(surfaceType);
        entityManager.flush();
        entityManager.refresh(surfaceType);
        return surfaceTypeMapper.toDTO(surfaceType);
    }

    @Override
    @Transactional
    public SurfaceTypeDTO update(SurfaceTypeDTO surfaceType) {
        SurfaceType updatedSurfaceType = entityManager.merge(surfaceTypeMapper.mapToSurfaceType(surfaceType));
        if (updatedSurfaceType == null || updatedSurfaceType.getIsDeleted()) {
            throw new NotFoundException("Surface type not found");
        }
        return surfaceTypeMapper.toDTO(updatedSurfaceType);
    }

    @Override
    @Transactional
    public SurfaceTypeDTO delete(Long id) {

        SurfaceType surfaceType = entityManager.find(SurfaceType.class, id);
        if (surfaceType == null || surfaceType.getIsDeleted()) {
            throw new NotFoundException("Surface type not found");
        }
        surfaceType.delete();
        entityManager.flush();
        return surfaceTypeMapper.toDTO(surfaceType);
    }

    @Override
    @Transactional
    public SurfaceTypeDTO[] getAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SurfaceType> cq = cb.createQuery(SurfaceType.class);
        Root<SurfaceType> courtRoot = cq.from(SurfaceType.class);

        Predicate isDeletedPredicate = cb.isFalse(courtRoot.get("isDeleted"));
        cq.where(isDeletedPredicate);

        List<SurfaceType> resultList = entityManager.createQuery(cq).getResultList();

        return surfaceTypeMapper.toDTOList(resultList).toArray(SurfaceTypeDTO[]::new);
    }
}
