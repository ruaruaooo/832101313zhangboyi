# calculator-back-end
函数claculate通过接受前端通过/claculator传来的POST请求，获取前端传来的算式并进行计算，计算完成后将算式及其结果一起存入绑定的数据库的result表中

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
获取前端“获取历史记录”的请求，向数据库的result表发送请求，获得result表中的记录，并通过json形式送回前端，前端再通过v-for在<table>组件中将历史记录显示出来

@GetMapping("/history")
    public List<Result> test(){
        QueryWrapper<Result> resultQueryWrapper=new QueryWrapper<>();//QueryWrapper：构造器
        return resultService.list();
    }
后端从前端获取存款时间，将时间与后台数据库中保存的利率表中进行比较，向前端返回该时间对应的利率，前端获取利率后进行利息计算并将结果在web页面上进行输出。下面只列出存款利率的获取，贷款利率获取部分在GitHub的后端部分中有：

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
    }

————————————————
版权声明：本文为CSDN博主「102101218李恒欣」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/obeyyourheart/article/details/133910238
