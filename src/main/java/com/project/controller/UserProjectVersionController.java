package com.project.controller;

import com.project.entity.UserProjectVersion;
import com.project.service.UserProjectVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/attendee")
public class UserProjectVersionController {
    @Autowired
    private UserProjectVersionService userProjectVersionService;
    @PostMapping("/{userProjectVersionId}/edit")
    public String editAttendee(@PathVariable Integer userProjectVersionId,
                               @ModelAttribute UserProjectVersion attendee,
                               RedirectAttributes redirectAttributes) {
        UserProjectVersion upv = userProjectVersionService.findById(userProjectVersionId);
        upv.setAdd(attendee.isAdd());
        upv.setEdit(attendee.isEdit());
        upv.setDelete(attendee.isDelete());
        try {
            userProjectVersionService.editUserProjectVersion(upv);
            redirectAttributes.addFlashAttribute("message", "Updated Attendee Successfully");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        return "redirect:/version/"+upv.getProjectVersion().getProjectVersionId()+"/attendees";
    }
    @PostMapping("/{userProjectVersionId}/delete")
    public String deleteAttendee(@PathVariable Integer userProjectVersionId,
                                 RedirectAttributes redirectAttributes) {
        Integer projectVersionId = userProjectVersionService.findById(userProjectVersionId).getProjectVersion().getProjectVersionId();
        try {
            userProjectVersionService.deleteUserProjectVersion(userProjectVersionId);
            redirectAttributes.addFlashAttribute("message", "Deleted Attendee Successfully");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        return "redirect:/version/"+projectVersionId+"/attendees";

    }


}
