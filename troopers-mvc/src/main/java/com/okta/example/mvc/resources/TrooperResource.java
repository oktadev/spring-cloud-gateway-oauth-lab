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
package com.okta.example.mvc.resources;

import com.okta.example.mvc.dao.StormtrooperDao;
import com.okta.example.mvc.models.Stormtrooper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path = "/")
public class TrooperResource {

    private final StormtrooperDao trooperDao;

    public TrooperResource(StormtrooperDao trooperDao) {
        this.trooperDao = trooperDao;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "foobar");
        return "welcome";
    }

    @GetMapping("/troopers")
    public String troopers(Model model, HttpServletRequest request) {
        model.addAttribute("troopers", trooperDao.listStormtroopers());
        return "troopers";
    }

    @GetMapping("/troopers/{id}")
    public String trooper(@PathVariable("id") String id, Model model, HttpServletRequest request) throws NotFoundException {

        Stormtrooper stormtrooper = trooperDao.getStormtrooper(id);
        if (stormtrooper == null) {
            throw new NotFoundException();
        }

        model.addAttribute("trooper", stormtrooper);
        return "trooper";
    }

//    @GetMapping(path = "/{id}")
//    public Stormtrooper getTrooper(@PathVariable("id")  String id) throws Exception {
//
//        Stormtrooper stormtrooper = trooperDao.getStormtrooper(id);
//        if (stormtrooper == null) {
//            throw new NotFoundException();
//        }
//        return stormtrooper;
//    }
//
//    @PostMapping(path = "/{id}")
//    public Stormtrooper updateTrooper(@PathVariable("id") String id, @RequestBody Stormtrooper updatedTrooper) {
//
//        return trooperDao.updateStormtrooper(id, updatedTrooper);
//    }
//
//    @PostMapping
//    public Stormtrooper addTrooper(@RequestBody Stormtrooper stormtrooper) {
//
//        return trooperDao.addStormtrooper(stormtrooper);
//    }
//
//    @DeleteMapping(path = "/{id}")
//    public void deleteTrooper(@PathVariable("id") String id) {
//        trooperDao.deleteStormtrooper(id);
//    }
}