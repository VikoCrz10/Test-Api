package com.test.userapi.Service;

import com.test.userapi.Model.Address;
import com.test.userapi.Model.User;
import com.test.userapi.Utils.AES;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private List<User> users = new ArrayList<>();

    private boolean applyFilter(User user, String field, String operator, String value){

        String fieldValue = "";

        switch (field){

            case "name":
                fieldValue = user.getName();
                break;

            case "email":
                fieldValue = user.getEmail();
                break;

            case "phone":
                fieldValue = user.getPhone();
                break;

            case "tax_id":
                fieldValue = user.getTaxId();
                break;
        }

        if(fieldValue == null) return false;

        switch (operator){

            case "co":
                return fieldValue.contains(value);

            case "eq":
                return fieldValue.equals(value);

            case "sw":
                return fieldValue.startsWith(value);

            case "ew":
                return fieldValue.endsWith(value);

            default:
                return false;
        }
    }
    private Comparator<User> getComparator(String sortedBy){

        switch (sortedBy){

            case "email":
                return Comparator.comparing(User::getEmail);

            case "name":
                return Comparator.comparing(User::getName);

            case "phone":
                return Comparator.comparing(User::getPhone);

            case "tax_id":
                return Comparator.comparing(User::getTaxId);

            case "created_at":
                return Comparator.comparing(User::getCreatedAt);

            default:
                return null;
        }
    }

    public List<User> getUsers(String sortedBy, String filter){

        List<User> result = new ArrayList<>(users);


        if(filter != null){

            String[] parts = filter.split("\\+");

            if(parts.length == 3){

                String field = parts[0];
                String operator = parts[1];
                String value = parts[2];

                result = result.stream()
                        .filter(user -> applyFilter(user, field, operator, value))
                        .toList();
            }
        }


        if(sortedBy != null){

            Comparator<User> comparator = getComparator(sortedBy);

            if(comparator != null){
                result = result.stream()
                        .sorted(comparator)
                        .toList();
            }
        }

        return result;
    }

    @PostConstruct
    public void initUsers(){

        User user1 = new User();
        user1.setId(UUID.randomUUID());
        user1.setEmail("user1@mail.com");
        user1.setName("user1");
        user1.setPhone("+52 5555555555");
        user1.setPassword("123456");
        user1.setTaxId("AARR990101XXX");
        user1.setCreatedAt(LocalDateTime.now());

        Address address1 = new Address();
        address1.setId(1L);
        address1.setName("workaddress");
        address1.setStreet("street No. 1");
        address1.setCountryCode("UK");

        user1.setAddresses(List.of(address1));

        User user2 = new User();
        user2.setId(UUID.randomUUID());
        user2.setEmail("user2@mail.com");
        user2.setName("user2");
        user2.setPhone("+52 5555555556");
        user2.setPassword("123456");
        user2.setTaxId("BBBB990101BBB");
        user2.setCreatedAt(LocalDateTime.now());

        User user3 = new User();
        user3.setId(UUID.randomUUID());
        user3.setEmail("user3@mail.com");
        user3.setName("user3");
        user3.setPhone("+52 5555555557");
        user3.setPassword("123456");
        user3.setTaxId("CCCC990101CCC");
        user3.setCreatedAt(LocalDateTime.now());

        users.add(user1);
        users.add(user2);
        users.add(user3);
    }

    public User createUser(User newUser ){

        boolean exists = users.stream()
                .anyMatch(user -> user.getTaxId().equals(newUser.getTaxId()));

        if(exists){
            throw new RuntimeException("El TaxId que intentas ingresar ya existe");
        }

        newUser.setId(UUID.randomUUID());
        newUser.setPassword(AES.encrypt(newUser.getPassword()));

        ZoneId madagascarZone = ZoneId.of("Indian/Antananarivo");
        LocalDateTime madagascarTime = LocalDateTime.now(madagascarZone);
        newUser.setCreatedAt(madagascarTime);

        users.add(newUser);

        return newUser;
    }

    public User login(String taxId, String password){

        User user = users.stream()
                .filter(u -> u.getTaxId().equals(taxId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String decryptedPassword = AES.decrypt(user.getPassword());

        if(!decryptedPassword.equals(password)){
            throw new RuntimeException("Password incorrecto");
        }

        return user;
    }

    public User updateUser(UUID id, User updatedUser){

        User user = users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if(updatedUser.getEmail() != null)
            user.setEmail(updatedUser.getEmail());

        if(updatedUser.getName() != null)
            user.setName(updatedUser.getName());

        if(updatedUser.getPhone() != null)
            user.setPhone(updatedUser.getPhone());

        if(updatedUser.getPassword() != null)
            user.setPassword(AES.encrypt(updatedUser.getPassword()));

        return user;
    }
    public void deleteUser(UUID id){

        User user = users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        users.remove(user);
    }


}