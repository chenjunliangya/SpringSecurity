package com.mb.mbqx.controller;

import com.mb.mbqx.util.Result;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("hello")
    @PreAuthorize("hasAuthority('test:hello')")
    public Result hello(){
        return Result.ok("hello security");
    }

    @GetMapping("index")
    @PreAuthorize("hasAuthority('test:index')")
    public Result index(){
        return Result.ok("hello index");
    }

    @GetMapping("update")
    @PreAuthorize("hasAuthority('test:update')")
    public Result update(){
        return Result.ok("hello update");
    }

    @GetMapping("delete")
    @PreAuthorize("hasAuthority('test:delete')")
    public Result delete(){
        return Result.ok("hello delete");
    }


}
