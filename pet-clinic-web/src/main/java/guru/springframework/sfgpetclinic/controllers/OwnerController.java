package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/owners")
@Controller
public class OwnerController {
    private static final String CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    public static final String FIND_OWNERS = "owners/findOwners";
    public static final String OWNERS_LIST = "owners/ownersList";

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping("/find")
    public String findOwners(Model model) {
        model.addAttribute("owner", Owner.builder().build());
        return FIND_OWNERS;
    }

    @GetMapping
    public String processFindForm(Owner owner, BindingResult result, Model model) {
        if ( owner.getLastName() == null ) {
            owner.setLastName("");
        }

        List<Owner> owners = ownerService.findAllByLastNameLike("%" + owner.getLastName() + "%");
        if ( owners == null || owners.isEmpty() ) {
            result.rejectValue("lastName", "notFound", "not found");
            return FIND_OWNERS;
        } else if ( owners.size() == 1 ) {
            return "redirect:/owners/" + owners.get(0).getId();
        } else {
            model.addAttribute("selections", owners);
            return OWNERS_LIST;
        }
    }

    @GetMapping("/{owner_id}")
    public ModelAndView showOwner(@PathVariable("owner_id") Long ownerId) {
        ModelAndView mav = new ModelAndView("owners/ownerDetails");
        mav.addObject("owner", ownerService.findById(ownerId));
        return mav;
    }

    @GetMapping("/new")
    public String initCreationForm(Model model) {
        model.addAttribute("owner", Owner.builder().build());
        return CREATE_OR_UPDATE_OWNER_FORM;
    }

    @PostMapping("/new")
    public String processCreationForm(@Valid Owner owner, BindingResult result) {
        if ( result.hasErrors() ) {
            return CREATE_OR_UPDATE_OWNER_FORM;
        }

        Owner savedOwner = ownerService.save(owner);
        return "redirect:/owners/" + savedOwner.getId();
    }

    @GetMapping("/{owner_id}/edit")
    public String initUpdateForm(@PathVariable("owner_id") Long ownerId, Model model) {
        model.addAttribute("owner", ownerService.findById(ownerId));
        return CREATE_OR_UPDATE_OWNER_FORM;
    }

    @PostMapping("/{owner_id}/edit")
    public String processUpdateForm(@Valid Owner owner, BindingResult result, @PathVariable("owner_id") Long ownerId) {
        if ( result.hasErrors() ) {
            return CREATE_OR_UPDATE_OWNER_FORM;
        }

        owner.setId(ownerId);
        Owner savedOwner = ownerService.save(owner);
        return "redirect:/owners/" + savedOwner.getId();
    }

}
