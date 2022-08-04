package com.himera.Messengermongo.repository;

import com.himera.Messengermongo.models.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends MongoRepository<Chat, String> {
    Chat findByName(String name);
}
