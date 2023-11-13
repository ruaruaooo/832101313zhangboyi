package com.lhx.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhx.dao.Borrow;
import com.lhx.dao.Result;
import com.lhx.dao.Rest;
import com.lhx.service.ResultService;
import com.lhx.service.impl.ResultServiceImpl;
import com.lhx.service.RestService;
import com.lhx.service.impl.RestServiceImpl;
import com.lhx.service.BorrowService;
import com.lhx.service.impl.BorrowServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class HelloWorldController {
    @Autowired
    ResultServiceImpl resultService;

    @Autowired
    RestServiceImpl restService;

    @Autowired
    BorrowServiceImpl borrowService;

    public String test;
    @PostMapping("/claculator")
    public String claculate(@RequestBody Map<String,Object> map){
        String result=map.get("result").toString();
        String resultstr=map.get("result").toString();
        String op="";
        if(result.indexOf("+")!=-1){
            Double num1=Double.valueOf(result.substring(0,result.indexOf("+")));
            Double num2=Double.valueOf(result.substring(result.indexOf("+")+1));
            Double res=num1+num2;
            result=Double.toString(res);
        }
        else if(result.indexOf("-")!=-1){
            Double num1=Double.valueOf(result.substring(0,result.indexOf("-")));
            Double num2=Double.valueOf(result.substring(result.indexOf("-")+1));
            Double res=num1-num2;
            result=Double.toString(res);
        }
        else if(result.indexOf("×")!=-1){
            Double num1=Double.valueOf(result.substring(0,result.indexOf("×")));
            Double num2=Double.valueOf(result.substring(result.indexOf("×")+1));
            Double res=num1*num2;
            result=Double.toString(res);
        }
        else if(result.indexOf("÷")!=-1){
            Double num1=Double.valueOf(result.substring(0,result.indexOf("÷")));
            Double num2=Double.valueOf(result.substring(result.indexOf("÷")+1));
            if(num2==0.0){
                result="ERROR";
            }
            else {
                Double res = num1 * num2;
                result = Double.toString(res);
            }
        }
        else if(result.indexOf("ln")!=-1){
            Double num1=Double.valueOf(result.replace("ln",""));
            Double res=Math.log(num1);
            result=Double.toString(res);
        }
        else if(result.indexOf("sin")!=-1){
            Double num1=Double.valueOf(result.replace("sin",""));
            Double res=Math.sin(num1);
            result=Double.toString(res);
        }
        else if(result.indexOf("cos")!=-1){
            Double num1=Double.valueOf(result.replace("cos",""));
            Double res=Math.cos(num1);
            result=Double.toString(res);
        }
        else if(result.indexOf("tan")!=-1){
            Double num1=Double.valueOf(result.replace("tan",""));
            Double res=Math.tan(num1);
            result=Double.toString(res);
        }
        else if(result.indexOf("!")!=-1){
            Integer num1=Integer.valueOf(result.replace("!",""));
            Double res=1.0;
            for(Integer i=num1;i>0;i--){
                res*=i;
            }
            result=Double.toString(res);
        }
        else if(result.indexOf("√")!=-1){
            Double num1=Double.valueOf(result.replace("√",""));
            Double res=Math.sqrt(num1);
            result=Double.toString(res);
        }
        else if(result.indexOf("^")!=-1){
            Double num1=Double.valueOf(result.substring(0,result.indexOf("^")));
            Double num2=Double.valueOf(result.substring(result.indexOf("^")+1));
            Double res=Math.pow(num1,num2);
            result=Double.toString(res);
            if(num1==0)
                result="0";
        }

        resultService.save(new Result(resultstr,result));//将数据存入数据库

        return result;
    }

    @GetMapping("/history")
    public List<Result> test(){
        QueryWrapper<Result> resultQueryWrapper=new QueryWrapper<>();//QueryWrapper：构造器
        return resultService.list();
    }

    @PostMapping("/restclaculator")
    public String restclaculator(@RequestBody Map<String,Object> map){
        QueryWrapper<Rest> restQueryWrapper=new QueryWrapper<>();
        double year= Double.valueOf((String) map.get("result").toString());
        Double temp=0.0;
        if(0.0<year&&year<=0.5){
            restQueryWrapper.eq("rest_year",0.5);
            Rest rest = restService.getOne(restQueryWrapper);
            temp=rest.getRestRate();
        }
        else if(0.5<year&&year<=1){
            restQueryWrapper.eq("rest_year",1);
            Rest rest = restService.getOne(restQueryWrapper);
            temp=rest.getRestRate();
        }
        else if(1<year&&year<=2){
            restQueryWrapper.eq("rest_year",2);
            Rest rest = restService.getOne(restQueryWrapper);
            temp=rest.getRestRate();
        }
        else if(2<year&&year<=3){
            restQueryWrapper.eq("rest_year",3);
            Rest rest = restService.getOne(restQueryWrapper);
            temp=rest.getRestRate();
        }
        else if(3<year){
            restQueryWrapper.eq("rest_year",4);
            Rest rest = restService.getOne(restQueryWrapper);
            temp=rest.getRestRate();
        }
        String result=Double.toString(temp);
        return result;

        //List<Rest> list = new ArrayList<>();

        //list.add(new Rest(0.0,0.0));

        //List<Rest> restList = restService.list();

        /*if(restList.size()>1){
            for(int i=0;i<list.size();i++){
                list.add(restList.get(i));
                if(list.get(i).getRestYear()<year&&year<=list.get(i+1).getRestYear()){

                }
            }
        }*/
    }

    @PostMapping("/borrowclaculator")
    public String borrowclaculator(@RequestBody Map<String,Object> map){
        QueryWrapper<Borrow> borrowQueryWrapper=new QueryWrapper<>();
        double year= Double.valueOf((String) map.get("result").toString());
        Double temp=0.0;
        if(0.0<year&&year<=0.5){
            borrowQueryWrapper.eq("borrow_year",0.5);
            Borrow borrow = borrowService.getOne(borrowQueryWrapper);
            temp=borrow.getBorrowRate();
        }
        else if(0.5<year&&year<=1){
            borrowQueryWrapper.eq("borrow_year",1);
            Borrow borrow = borrowService.getOne(borrowQueryWrapper);
            temp=borrow.getBorrowRate();
        }
        else if(1<year&&year<=2){
            borrowQueryWrapper.eq("borrow_year",2);
            Borrow borrow = borrowService.getOne(borrowQueryWrapper);
            temp=borrow.getBorrowRate();
        }
        else if(2<year&&year<=3){
            borrowQueryWrapper.eq("borrow_year",3);
            Borrow borrow = borrowService.getOne(borrowQueryWrapper);
            temp=borrow.getBorrowRate();
        }
        else if(3<year){
            borrowQueryWrapper.eq("borrow_year",4);
            Borrow borrow = borrowService.getOne(borrowQueryWrapper);
            temp=borrow.getBorrowRate();
        }
        String result=Double.toString(temp);
        return result;
        //List<Rest> list = new ArrayList<>();

        //list.add(new Rest(0.0,0.0));

        //List<Rest> restList = restService.list();

        /*if(restList.size()>1){
            for(int i=0;i<list.size();i++){
                list.add(restList.get(i));
                if(list.get(i).getRestYear()<year&&year<=list.get(i+1).getRestYear()){

                }
            }
        }*/
    }
}
