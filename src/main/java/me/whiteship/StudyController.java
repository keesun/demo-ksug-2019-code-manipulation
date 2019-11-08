package me.whiteship;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StudyController {

    private final LocationRepository locationRepository;
    private final StudyRepository studyRepository;

    @GetMapping("/study/{id}")
    public Study getStudy(@PathVariable Long id) {
        Optional<Study> byId = studyRepository.findById(id);
        Study existingStudy = byId.orElseThrow(() -> new IllegalArgumentException());
        return existingStudy;
    }

    @PostConstruct
    public void initData() {
        Location d2factory = new Location();
        d2factory.setName("D2 Factory");

        Study study = new Study();
        study.setLocation(locationRepository.save(d2factory));
        study.setName("KSUG 2019 가을 세미나");
        log.info("new study id : '" + studyRepository.save(study).getId() + "'");
    }

}
