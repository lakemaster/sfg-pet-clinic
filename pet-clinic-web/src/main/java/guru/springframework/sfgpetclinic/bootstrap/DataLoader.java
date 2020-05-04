package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.*;
import guru.springframework.sfgpetclinic.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;
    private final VisitService visitService;

    public DataLoader(OwnerService ownerService,
                      VetService vetService,
                      PetTypeService petTypeService,
                      SpecialityService specialityService,
                      VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) {

        if (petTypeService.findAll().size() == 0) {
            loadData();
        }
    }

    private void loadData() {
        PetType dog = new PetType();
        dog.setName("Dog");
        dog = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("Cat");
        cat = petTypeService.save(cat);

        Speciality radiology = new Speciality();
        radiology.setDescription("Radiology");
        Speciality surgery = new Speciality();
        radiology.setDescription("Surgery");
        Speciality dentistry = new Speciality();
        radiology.setDescription("Dentistry");
        radiology = specialityService.save(radiology);
        surgery = specialityService.save(surgery);
        dentistry = specialityService.save(dentistry);


        Owner mike = new Owner();
        mike.setFirstName("Micheal");
        mike.setLastName("Weston");
        mike.setAddress("123 Brickerel");
        mike.setCity("Miami");
        mike.setTelephone("943218902854");

        Pet rosco = new Pet();
        rosco.setPetType(dog);
        rosco.setOwner(mike);
        rosco.setName("Rosco");
        rosco.setBirthDate(LocalDate.now());
        mike.getPets().add(rosco);

        ownerService.save(mike);

        Owner fiona = new Owner();
        fiona.setFirstName("Fiona");
        fiona.setLastName("Glenanne");
        fiona.setAddress("567 Main Street");
        fiona.setCity("Los Angeles");
        fiona.setTelephone("349857613");

        Pet mizi = new Pet();
        mizi.setPetType(cat);
        mizi.setName("Mizi");
        mizi.setBirthDate(LocalDate.of(2020, 1, 22));
        mizi.setOwner(fiona);
        fiona.getPets().add(mizi);

        ownerService.save(fiona);

        Visit visit = new Visit();
        visit.setDate(LocalDate.of(2020, 2, 23));
        visit.setPet(mizi);
        visit.setDescription("Sneezy Kitty");

        visitService.save(visit);

        System.out.println("Loaded Owners ...");

        Vet sam = new Vet();
        sam.setFirstName("Sam");
        sam.setLastName("Axe");
        sam.getSpecialities().add(radiology);
        vetService.save(sam);

        Vet jessi = new Vet();
        jessi.setFirstName("Jessi");
        jessi.setLastName("Porter");
        jessi.getSpecialities().add(surgery);
        vetService.save(jessi);

        System.out.println("Loaded Vets ...");
    }
}
