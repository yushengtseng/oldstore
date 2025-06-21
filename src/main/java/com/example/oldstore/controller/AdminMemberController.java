package com.example.oldstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.oldstore.model.dto.UserDto;
import com.example.oldstore.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/members")
public class AdminMemberController {

	@Autowired
	private UserService userService;
	
	// 顯示會員列表
	@GetMapping
	public String listMembers(Model model) {
		List<UserDto> users = userService.getAllUsers();
		model.addAttribute("users", users);
		return "admin/member/list";
	}
	
	// 顯示新增會員畫面
	@GetMapping("/create")
	public String showCreateForm(Model model) {
		model.addAttribute("userDto", new UserDto());
		return "admin/member/create";
	}
	
	// 提交新增會員
	@PostMapping
	public String createMember(@ModelAttribute @Valid UserDto userDto, BindingResult result) {
		if(result.hasErrors()) {
			return "admin/member/create";
		}
		userService.createUser(userDto);
		return "redirect:/admin/members";
	}
	
	// 顯示編輯畫面
	@GetMapping("/{id}")
	public String showEditForm(@PathVariable Integer id, Model model) {
		UserDto userDto = userService.getUserById(id);
		
		if("MasterAdmin".equals(userDto.getUsername())) {
			throw new IllegalArgumentException("不可修改 MasterAdmin 帳號。");
		}
		
		model.addAttribute("userDto", userDto);
		return "admin/member/edit";
	}
	
	// 提交修改
	@PutMapping("/{id}")
	public String updateMember(@PathVariable Integer id,
							   @ModelAttribute @Valid UserDto userDto,
							   BindingResult result) {
		if(result.hasErrors()) {
			return "admin/member/edit";
		}
		userService.updateUser(id, userDto);
		return "redirect:/admin/members";
	}
	
	// 刪除會員
	@DeleteMapping("/{id}")
	public String deleteMember(@PathVariable Integer id) {
		userService.deleteUser(id);
		return "redirect:/admin/members";
	}
	
	// 切換權限(USER與ADMIN)
	@PutMapping("/{id}/role/{role}")
	public String changeUserRole(@PathVariable Integer id,
								 @PathVariable String role) {
		userService.updateUserRole(id, role);
		return "redirect:/admin/members";
	}
}
