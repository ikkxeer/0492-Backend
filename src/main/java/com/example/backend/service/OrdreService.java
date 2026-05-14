package com.example.backend.service;

import com.example.backend.domain.Ordre;
import com.example.backend.domain.Pale;
import com.example.backend.domain.UserAccount;
import com.example.backend.domain.Tracking;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.backend.repo.OrdreRepository;
import com.example.backend.repo.PaleRepository;
import com.example.backend.repo.UserRepository;
import com.example.backend.web.dto.OrdreCreateDTO;
import com.example.backend.web.dto.OrdreDTO;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service per les ordres
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@Service
public class OrdreService {

    @Autowired
    private OrdreRepository ordreRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaleRepository paleRepository;

    @Autowired
    private com.example.backend.repo.GrupMozosRepository grupMozosRepository;

    // rolId=1: ADMIN totes les ordres
    @Transactional(readOnly = true)
    public List<OrdreDTO> findAll() {
        return ordreRepository.findAll().stream()
                .map(this::convertToDTOWithPesRecalculated)
                .collect(Collectors.toList());
    }

    private OrdreDTO convertToDTOWithPesRecalculated(Ordre o) {
        OrdreDTO dto = new OrdreDTO(o);
        if (dto.paleIds != null && !dto.paleIds.isEmpty()) {
            List<Pale> pales = paleRepository.findAllById(dto.paleIds);
            double suma = pales.stream()
                    .filter(p -> p.getPes() != null)
                    .mapToDouble(p -> p.getPes().doubleValue())
                    .sum();
            dto.pesTotal = suma;
        } else if (dto.pesTotal == null) {
            dto.pesTotal = 0.0;
        }
        return dto;
    }

    // rolId=2: GESTOR ordres on ell és el gestor
    @Transactional(readOnly = true)
    public List<OrdreDTO> findByGestor(String nom) {
        return ordreRepository.findByGestorNom(nom).stream()
                .map(this::convertToDTOWithPesRecalculated)
                .collect(Collectors.toList());
    }

    // rolId=3: MOZO ordres assignades al seu grup
    @Transactional(readOnly = true)
    public List<OrdreDTO> findByMozoGrupo(String nomMozo) {
        return ordreRepository.findByGrupoMozoUsuarioNom(nomMozo).stream()
                .map(this::convertToDTOWithPesRecalculated)
                .collect(Collectors.toList());
    }

    // rolId=4: TRANSPORTISTA ordres assignades a ell
    @Transactional(readOnly = true)
    public List<OrdreDTO> findByTransportista(String nom) {
        return ordreRepository.findByTransportistaNom(nom).stream()
                .map(this::convertToDTOWithPesRecalculated)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<OrdreDTO> findByIdentificador(String id) {
        return ordreRepository.findByIdentificadorWithPales(id)
                .map(this::convertToDTOWithPesRecalculated);
    }

    // Crear orden
    @Transactional
    public OrdreDTO crear(OrdreCreateDTO dto) {
        Ordre o = new Ordre();

        // Datos básicos
        o.setAdreca(dto.adreca);
        o.setCiutat(dto.ciutat);
        o.setCp(dto.cp);
        o.setTelefon(dto.telefon);
        o.setPrioritat(dto.prioritat);
        o.setTemporada(dto.temporada);
        o.setPreu(BigDecimal.valueOf(dto.preu != null ? dto.preu : 0.0));
        o.setTendaDestinataria(dto.tendaDestinataria);

        // Estado inicial
        o.setEstat("ESBORRANY");
        o.setData_creacio(LocalDateTime.now());

        // Generar Identificador
        o.setIdentificador("ORD-" + System.currentTimeMillis() % 100000);

        // Asignar Gestor
        if (dto.gestorId != null) {
            userRepository.findById(dto.gestorId).ifPresent(o::setGestor);
        }

        // Asignar Pales seleccionados
        if (dto.paleIds != null && !dto.paleIds.isEmpty()) {
            List<Pale> palesSeleccionados = paleRepository.findAllById(dto.paleIds);
            o.setPales(palesSeleccionados);

            // Calculamos el peso total para persistirlo en la entidad
            BigDecimal sumaPes = palesSeleccionados.stream()
                    .map(Pale::getPes)
                    .filter(pes -> pes != null)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            o.setPesTotal(sumaPes);
            o.setQuantitatPales(palesSeleccionados.size());

            // Cambiar estado de los pales a 'pendent' (en borrador)
            palesSeleccionados.forEach(p -> p.setEstat("pendent"));
        }

        Ordre guardada = ordreRepository.save(o);
        return new OrdreDTO(guardada);
    }

    // Actualizar una orden existente
    @Transactional
    public OrdreDTO actualizar(Integer id, OrdreCreateDTO dto) {
        Ordre o = ordreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("L'ordre no existeix"));

        // Actualizar datos básicos
        o.setAdreca(dto.adreca);
        o.setCiutat(dto.ciutat);
        o.setCp(dto.cp);
        o.setTelefon(dto.telefon);
        o.setPrioritat(dto.prioritat);
        o.setTemporada(dto.temporada);
        o.setPreu(BigDecimal.valueOf(dto.preu != null ? dto.preu : 0.0));
        o.setTendaDestinataria(dto.tendaDestinataria);

        // Si se pasan nuevos pales, actualizamos la lista
        if (dto.paleIds != null) {
            // Liberar pales antiguos
            o.getPales().forEach(p -> p.setEstat("disponible"));

            List<Pale> nuevosPales = paleRepository.findAllById(dto.paleIds);

            // Calcular nuevo peso total
            BigDecimal sumaPes = nuevosPales.stream()
                    .map(Pale::getPes)
                    .filter(pes -> pes != null)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            o.setPesTotal(sumaPes);
            o.setQuantitatPales(nuevosPales.size());

            // Reservar nuevos pales (estat pendent en borrador)
            nuevosPales.forEach(p -> p.setEstat("pendent"));
            o.setPales(nuevosPales);
        }

        Ordre guardada = ordreRepository.save(o);
        return new OrdreDTO(guardada);
    }

    // Eliminar una ordre per ID
    @Transactional
    public void eliminar(Integer id) {
        Optional<Ordre> ordreOpt = ordreRepository.findById(id);
        if (ordreOpt.isPresent()) {
            Ordre o = ordreOpt.get();
            if (o.getPales() != null) {
                o.getPales().forEach(p -> p.setEstat("disponible"));
            }

            ordreRepository.delete(o);
        } else {
            throw new RuntimeException("L'ordre no existeix");
        }
    }

    // Confirmar orden
    @Transactional
    public OrdreDTO confirmar(Integer id, String nomGrupMozos, Integer gestorId) {
        Ordre o = ordreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("L'ordre no existeix"));

        if (!"ESBORRANY".equals(o.getEstat())) {
            throw new RuntimeException("Només es poden confirmar ordres en estat ESBORRANY");
        }

        o.setEstat("PENDENT_PREPARACIO");

        if (nomGrupMozos != null && !nomGrupMozos.isEmpty()) {
            grupMozosRepository.findByNom(nomGrupMozos).ifPresent(o::setGrupMozos);
        }

        if (gestorId != null) {
            userRepository.findById(gestorId).ifPresent(o::setGestor);
        }

        String codiAlbara = "ALB-" + LocalDateTime.now().getYear() + "-" + (10000 + o.getId_ordre());
        o.setCodiAlbara(codiAlbara);

        if (o.getPales() != null) {
            o.getPales().forEach(p -> p.setEstat("assignada"));
        }

        Tracking t = new Tracking();
        t.setOrdre(o);
        t.setEtapa("PENDENT_PREPARACIO");
        t.setTimestamp(LocalDateTime.now());
        t.setNotes("Ordre confirmada");
        if (gestorId != null) {
            userRepository.findById(gestorId).ifPresent(t::setUsuari);
        }
        if (o.getHistorial() == null) {
            o.setHistorial(new java.util.ArrayList<>());
        }
        o.getHistorial().add(t);

        Ordre guardada = ordreRepository.save(o);
        return new OrdreDTO(guardada);
    }

    @Transactional
    public OrdreDTO canviarEstat(Integer id, String nouEstat, Integer userId) {
        Ordre o = ordreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("L'ordre no existeix"));

        o.setEstat(nouEstat);

        // Si l'ordre s'entrega, les pales passen a estar assignades (finalitzades)
        if ("ENTREGAT".equals(nouEstat) && o.getPales() != null) {
            o.getPales().forEach(p -> p.setEstat("assignada"));
        }

        Tracking t = new Tracking();
        t.setOrdre(o);
        t.setEtapa(nouEstat);
        t.setTimestamp(LocalDateTime.now());
        t.setNotes(formatEstat(nouEstat));
        if (userId != null) {
            userRepository.findById(userId).ifPresent(t::setUsuari);
        }
        if (o.getHistorial() == null) {
            o.setHistorial(new java.util.ArrayList<>());
        }
        o.getHistorial().add(t);

        Ordre guardada = ordreRepository.save(o);
        return new OrdreDTO(guardada);
    }

    @Transactional
    public OrdreDTO assignarTransportista(Integer id, Integer transportistaId) {
        Ordre o = ordreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("L'ordre no existeix"));

        userRepository.findById(transportistaId).ifPresent(o::setTransportista);

        Ordre guardada = ordreRepository.save(o);
        return new OrdreDTO(guardada);
    }

    private String formatEstat(String estat) {
        if (estat == null)
            return "";
        switch (estat) {
            case "PENDENT_PREPARACIO":
                return "Pendent preparació";
            case "PREPARACIO_EN_CURS":
                return "Preparació en curs";
            case "PREPARACIO_FINALITZADA":
                return "Preparació finalitzada";
            case "EN_TRANSIT":
                return "En trànsit";
            case "ENTREGAT":
                return "Entregat";
            default:
                return estat;
        }
    }
}