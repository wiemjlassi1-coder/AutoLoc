package tn.esprit.tpfoyer.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.domain.Agence;
import tn.esprit.tpfoyer.exception.ResourceNotFoundException;
import tn.esprit.tpfoyer.repository.IAgenceRepository;

@Service
@RequiredArgsConstructor
public class AgenceServiceImpl implements IAgenceService {

    private final IAgenceRepository agenceRepository;

    @Override
    public Agence create(Agence agence) {
        if (agence.getIdAgence() != null) {
            throw new IllegalArgumentException(
                    "Une nouvelle agence ne doit pas avoir d'identifiant");
        }
        verifier(agence);
        return agenceRepository.save(agence);
    }

    @Override
    public Agence findById(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", id));
    }

    @Override
    public List<Agence> findAll() {
        return agenceRepository.findAll();
    }

    @Override
    public Agence update(Long id, Agence agence) {
        verifier(agence);
        Agence existant = findById(id);
        existant.setNom(agence.getNom());
        existant.setVille(agence.getVille());
        existant.setAdresse(agence.getAdresse());
        existant.setTelephone(agence.getTelephone());
        return agenceRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!agenceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Agence", id);
        }
        agenceRepository.deleteById(id);
    }

    private static void verifier(Agence agence) {
        if (agence.getNom() == null || agence.getNom().isBlank()) {
            throw new IllegalArgumentException("Le nom de l'agence est obligatoire");
        }
        if (agence.getVille() == null || agence.getVille().isBlank()) {
            throw new IllegalArgumentException("La ville de l'agence est obligatoire");
        }
        if (agence.getAdresse() == null || agence.getAdresse().isBlank()) {
            throw new IllegalArgumentException("L'adresse de l'agence est obligatoire");
        }
    }
}
