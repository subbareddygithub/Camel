/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ibm;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.support.DefaultMessage;

import static java.util.Objects.nonNull;
import static org.apache.camel.Exchange.HTTP_RESPONSE_CODE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@Service("userService")
public class UserServiceImpl implements UserService {

    private final Map<Integer, User> users = new TreeMap<>();

    public UserServiceImpl() {
        users.put(1, new User(1, "John Coltrane"));
        users.put(2, new User(2, "Miles Davis"));
        users.put(3, new User(3, "Sonny Rollins"));
    }

    @Override
    public User findUser(Integer id) {
        return users.get(id);
    }

    @Override
    public void findUserById(Exchange exchange) {
        // return users.get(id);
        Integer userId = exchange.getMessage().getHeader("id", Integer.class);
        User returnedUser = users.get(userId);

        if (nonNull(returnedUser)) {
            Message message = new DefaultMessage(exchange.getContext());
            message.setBody(returnedUser);
            exchange.setMessage(message);
            exchange.getMessage().setHeader(HTTP_RESPONSE_CODE, HttpStatus.OK.value());
            //log.info("User Data found for {}", userId);
        } else {
            exchange.getMessage().setHeader(HTTP_RESPONSE_CODE, NOT_FOUND.value());
            //log.warn("User Data not found for {}", userId);
        }
    }

    @Override
    public Collection<User> findUsers() {
        return users.values();
    }

    @Override
    public void updateUser(User user) {
        users.put(user.getId(), user);
    }
    
    @Override
    public User createUser(User user) {
        return user;
    }


}