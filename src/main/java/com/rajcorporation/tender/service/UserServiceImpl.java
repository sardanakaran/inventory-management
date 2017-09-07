package com.rajcorporation.tender.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rajcorporation.tender.security.auth.model.Authority;
import com.rajcorporation.tender.security.auth.model.User;
import com.rajcorporation.tender.security.auth.repository.AuthorityRepository;
import com.rajcorporation.tender.security.auth.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthorityRepository authRepo;

	@Override
	@PreAuthorize("hasRole('USER')")
	public User findByUsername(String username) throws UsernameNotFoundException {
		User u = userRepository.findByUsername(username);
		return u;
	}

	@PreAuthorize("hasRole('ADMIN')")
	public User findById(Long id) throws AccessDeniedException {
		User u = userRepository.findOne(id);
		return u;
	}

	@PreAuthorize("hasRole('ADMIN')")
	public List<User> findAll() throws AccessDeniedException {
		List<User> result = userRepository.findAll();
		return result;
	}

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public User add(User user) {
		return userRepository.save(user);
	}

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	@Transactional
	public User addAuthority(Long id, Long authorityId) {
		User user = findById(id);
		Authority authority = authRepo.findOne(authorityId);
		if (user.getAuthorities() != null && !user.getAuthorities().contains(authority)) {
			user.getAuthorities().add(authority);
		}
		return user;
	}

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	@Transactional
	public User removeAuthority(Long id, Long authorityId) {
		User user = findById(id);
		Authority authority = authRepo.findOne(authorityId);
		if (!Objects.isNull(user.getAuthorities()))
			user.getAuthorities().remove(authority);
		return user;
	}

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public List<User> getUsers(Long id) {
		if (id == null)
			return userRepository.findAll();
		else {
			User user = userRepository.findOne(id);
			if (user != null)
				return Arrays.asList(user);
			else
				return Collections.emptyList();
		}

	}

}
