package sfgpetclinic;

import guru.springframework.sfgpetclinic.MyComponent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MyComponent.class)
class SfgPetClinicApplicationTests {

    @Test
    public void contextLoads() {
    }

}
