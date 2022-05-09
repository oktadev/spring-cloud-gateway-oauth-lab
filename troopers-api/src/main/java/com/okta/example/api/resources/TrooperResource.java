/*
 * Copyright 2016 Stormpath, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.okta.example.api.resources;

import com.okta.example.api.dao.StormtrooperDao;
import com.okta.example.api.models.Stormtrooper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(path = "/")
public class TrooperResource {

    private final StormtrooperDao trooperDao;

    public TrooperResource(StormtrooperDao trooperDao) {
        this.trooperDao = trooperDao;
    }

    @GetMapping("/")
    public Collection<Stormtrooper> getTroopers() {

        return trooperDao.listStormtroopers();
    }

    @GetMapping(path = "/{id}")
    public Stormtrooper getTrooper(@PathVariable("id")  String id) throws Exception {

        Stormtrooper stormtrooper = trooperDao.getStormtrooper(id);
        if (stormtrooper == null) {
            throw new NotFoundException();
        }
        return stormtrooper;
    }

    @PostMapping(path = "/{id}")
    public Stormtrooper updateTrooper(@PathVariable("id") String id, @RequestBody Stormtrooper updatedTrooper) {

        return trooperDao.updateStormtrooper(id, updatedTrooper);
    }

    @PostMapping
    public Stormtrooper addTrooper(@RequestBody Stormtrooper stormtrooper) {

        return trooperDao.addStormtrooper(stormtrooper);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteTrooper(@PathVariable("id") String id) {
        trooperDao.deleteStormtrooper(id);
    }
}