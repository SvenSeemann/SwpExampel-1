package messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import user.Role;
import user.User;
import user.UserRepository;

import java.util.Optional;

/**
 * Created by justusadam on 09/12/14.
 */
@Component
public class PostOffice {

    private final UserRepository users;

    private final MessageRepository repo;

    public static final Role senderRole = new Role("MESSAGE_SENDER");

    public static final Role receiverRole = new Role("MESSAGE_RECEIVER");

    @Autowired
    public PostOffice(UserRepository users, MessageRepository repo) {
        this.users = users;
        this.repo = repo;
    }

    public boolean sendMessage(long sender, long receiver, String message) {
        if (canSend(sender) && canReceive(receiver)){
            repo.save(new Message(message, sender, receiver));
            return true;
        }
        return false;
    }

    public boolean hasRole(User user, Role role) {
        return user.getRoles().contains(role);
    }

    public boolean hasRole(long userId, Role role) {
        Optional<User> x = users.findOne(userId);

        return x.isPresent() && hasRole(x.get(), role);
    }

    public boolean canSend(User user) {
        return hasRole(user, senderRole);
    }

    public boolean canSend(long userId) {
        return hasRole(userId, senderRole);
    }

    public boolean canReceive(User user) {
        return hasRole(user, receiverRole);
    }

    public boolean canReceive(long userId) {
        return hasRole(userId, receiverRole);
    }

}
