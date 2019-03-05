package pl.sda.mlr.miniblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.mlr.miniblog.entity.Role;
import pl.sda.mlr.miniblog.entity.User;
import pl.sda.mlr.miniblog.form.UserEditForm;
import pl.sda.mlr.miniblog.form.UserRegisterForm;
import pl.sda.mlr.miniblog.repository.PostRepository;
import pl.sda.mlr.miniblog.repository.RoleRepository;
import pl.sda.mlr.miniblog.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    public static final String ROLE_USER = "ROLE_USER";
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private UserEditForm userEditForm;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }
/*
    public void editUser(UserEditForm userEditForm, String userId) {
        User user = new User();
        Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));
        User userFromDatabase = userOptional.get();

        user.setId(userFromDatabase.getId());
        user.setRoles(userFromDatabase.getRoles());

        if (userEditForm.getFirstName() != null) {
            user.setFirstName(userEditForm.getFirstName());
        } else{
            user.setFirstName(userFromDatabase.getFirstName());
        }

        if (userEditForm.getLastName() != null) {
            user.setLastName(userEditForm.getLastName());
        } else{
            user.setLastName(userFromDatabase.getLastName());
        }

        if (userEditForm.getEmail() != null) {
            user.setEmail(userEditForm.getEmail());
        } else{
            user.setEmail(userFromDatabase.getEmail());
        }

        if (userEditForm.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userEditForm.getPassword()));
        } else{
            user.setPassword(passwordEncoder.encode(userFromDatabase.getPassword()));
        }

        userRepository.save(user);
    }
    */

    public void registerUser(UserRegisterForm userRegisterForm) {
        User user = new User();
        user.setEmail(userRegisterForm.getEmail());
        user.setFirstName(userRegisterForm.getFirstName());
        user.setLastName(userRegisterForm.getLastName());
        user.setPassword(passwordEncoder.encode(userRegisterForm.getPassword()));

//        Optional<Role> optionalRole = roleRepository.findByRoleName(ROLE_USER);
//        if(!optionalRole.isPresent()) {
//            Role role = new Role(ROLE_USER);
//            roleRepository.save(role);
//        }

        getORCreateDefaultRole(user);
        userRepository.save(user);
    }

    private void getORCreateDefaultRole(User user) {
        Role role = roleRepository.findByRoleName(ROLE_USER)
                .orElseGet(() -> roleRepository.save(new Role(ROLE_USER)));
//        Set<Role> roles = new HashSet<>();
//        roles.add(role);
//        user.setRoles(roles);
        user.addRole(role);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getSingleUser(Long userId) {
        return userRepository.findById(userId);
    }

    @Transactional
    public void changeFirstName(String userId, String firstName) {
        Long userIdLong = Long.valueOf(userId);
        userRepository.updateFirstName(firstName, userIdLong);
    }

    @Transactional
    public void changeLastName(String userId, String lastName) {
        Long userIdLong = Long.valueOf(userId);
        userRepository.updateLastName(lastName, userIdLong);
    }
}


/*
@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;


    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, PostRepository postRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public void registerUser(UserRegisterForm userRegisterForm){
        User user = new User();
        user.setEmail(userRegisterForm.getEmail());
        user.setFirstName(userRegisterForm.getFirstName());
        user.setLastName(userRegisterForm.getLastName());
        user.setPassword(passwordEncoder.encode(userRegisterForm.getPassword()));

        getOrCreateDefaultRole(user);
        userRepository.save(user);

//        Set<Role> roles = new HashSet<Role>();
//        roles.add(roleService.getRoleByName("ROLE_USER"));
//        user.setRoles(roles);
    }

    private void getOrCreateDefaultRole(User user) {
        Role role = roleRepository.findByRoleName("ROLE_USER")
                .orElseGet(()->roleRepository.save(new Role("ROLE_USER")));     //wykona się tylko wtedy, kiedy Optional z wyszukania z bazy będzie pusty; samo orElse niezależnie od Optionala wbije do bazy nowy rekord
//        HashSet<Role> roles = new HashSet<>();
//        roles.add(role);
//        user.setRoles(roles);
        user.addRole(role);
    }

}
*/