
<template>
  <div class="calculator" v-if=temp>
    <div class="result" style="grid-area: result">{{result}}</div>

    <button style="grid-area: ac" @click="Clear()">AC</button>
    <button style="grid-area: add" @click="ClickOp('+')">+</button>
    <button style="grid-area: sub" @click="ClickOp('-')">-</button>
    <button style="grid-area: mul" @click="ClickOp('×')">×</button>
    <button style="grid-area: div" @click="ClickOp('÷')">÷</button>
    <button style="grid-area: equal" @click="ClikcEqual()">=</button>
    <button style="grid-area: LN" @click="ClickOp('ln')">ln</button>
    <button style="grid-area: SIN" @click="ClickOp('sin')">sin</button>
    <button style="grid-area: COS" @click="ClickOp('cos')">cos</button>
    <button style="grid-area: TAN" @click="ClickOp('tan')">tan</button>
    <button style="grid-area: factorial" @click="ClickOp('!')">!</button>
    <button style="grid-area: SQRT" @click="ClickOp('√')">sqrt</button>
    <button style="grid-area: power" @click="ClickOp('^')">^</button>

    <button style="grid-area: number-1" @click="ClickNum('1')">1</button>
    <button style="grid-area: number-2" @click="ClickNum('2')">2</button>
    <button style="grid-area: number-3" @click="ClickNum('3')">3</button>
    <button style="grid-area: number-4" @click="ClickNum('4')">4</button>
    <button style="grid-area: number-5" @click="ClickNum('5')">5</button>
    <button style="grid-area: number-6" @click="ClickNum('6')">6</button>
    <button style="grid-area: number-7" @click="ClickNum('7')">7</button>
    <button style="grid-area: number-8" @click="ClickNum('8')">8</button>
    <button style="grid-area: number-9" @click="ClickNum('9')">9</button>
    <button style="grid-area: number-0" @click="ClickNum('0')">0</button>

    <button style="grid-area: dot" @click="ClickPoint('.')">.</button>
  </div>

  <div class="select" >
    <button class="select1" style="grid-area: select1" @click="ClikcSelect1()">基础计算器</button>
    <button class="select2" style="grid-area: select2" @click="ClikcSelect2()">利率计算器</button>
  </div>

  <table class="history" v-if=temp>
    <thead>
      <tr>
        <th>算式</th>
        <th>结果</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="(item,index) in historydata">
        <td>{{ item.resultString }}</td>
        <td>{{ item.result }}</td>
      </tr>
    </tbody>
  </table>
  
  <button class="gethistory" @click="GetHistory()" v-if=temp>获取历史记录</button>

  <div class="intersetcalculator" v-if=!temp>
    <input type="text" name="time" style="grid-area: time" v-model="year">
    <div class="settime" style="grid-area: settime" >时间(年)</div>
    <input type="text" name="money" style="grid-area: money"  v-model="money">
    <div class="setmoney" style="grid-area: setmoney">金额</div>
    <div class="restmoney" style="grid-area: restmoney">{{restmoney}}</div>
    <button class="getrestmoney" style="grid-area: getrestmoney" @click="ClickRestMoney()">查询存款利息</button>
    <div class="borrowmoney" style="grid-area: borrowmoney">{{borrowmoney}}</div>
    <button class="getborrowmoney" style="grid-area: getborrowmoney" @click="ClickBorrowMoney()">查询贷款利息</button>
  </div>
</template>

<script setup>

  import { reactive, ref } from 'vue';
  import axios from 'axios';
  const result=ref("0");
  const temp=ref(true);
  const historydata=ref({});
  const restmoney=ref();
  const borrowmoney=ref();
  const year=ref();
  const money=ref();

  const isOperator=false;//判断是否是第一个操作符
  const isNum=false;//判断是否是第一个参与运算的数
  const is_Point=false;//判断一个数字中是否出现两次小数点
  const isFactorial=false;//判断是否进行阶乘运算
  const restrate=0.0;
  const borrowrate=0.0;

  function ClickNum(number){
    if(!this.isFactorial){
      if(!this.isNum){
        if(number!='0')
        this.isNum=true;
        result.value=number;
      }
      else{
        result.value+=number;
      }
    }
    else{
      result.value='ERROR';
    } 
  }

  function ClickOp(operator){
    if(!this.isOperator){
      if(operator=='+'||operator=='-'||operator=='×'||operator=='÷'||operator=='^'){
        result.value+=operator;
        this.isNum=true;
      }
      else if(operator=='ln'||operator=='sin'||operator=='cos'||operator=='tan'||operator=='√'){
        if(this.isNum){
          result.value='ERROR';
        }
        else{
          this.isNum=true;
          result.value=operator;
        }
      }
      else if(operator=='!'){
        if(this.is_Point){
          result.value='ERROR';
        }
        else{
          result.value+=operator;
          this.isFactorial=true;
        }
      }
      this.isOperator=true;
      this.is_Point=false;
    }
    else{
      result.value='ERROR';
    }
  }

  function Clear(){
    this.isOperator=false;
    this.isNum=false;
    this.is_Point=false;
    this.isFactorial=false;
    result.value='0';
  }

  function ClickPoint(point){
    if(!this.is_Point){
        result.value+=point;
        this.is_Point=true;
        this.isNum=true;
    }
    else{
      result.value='ERROR';
    }
  }

  function ClikcEqual(){
    axios.post("http://localhost:8081/claculator",{result:result.value
    }).then(response=>{
        result.value=response.data;
    })
  }

  function ClikcSelect1(){
    this.temp=true;
  }

  function ClikcSelect2(){
    this.temp=false;
  }

  function GetHistory(){
    axios.get("http://localhost:8081/history").then(response=>{

        historydata.value=response.data;
        console.log(historydata);
        
    })
  }

  function ClickRestMoney(){
    axios.post("http://localhost:8081/restclaculator",{result:year.value
    }).then(response=>{
        this.restrate=response.data;
        restmoney.value=this.restrate*money.value*year.value;
    })
  }

  function ClickBorrowMoney(){
    axios.post("http://localhost:8081/borrowclaculator",{result:year.value
    }).then(response=>{
        this.borrowrate=response.data;
        borrowmoney.value=this.borrowrate*money.value*year.value;
    })
  }
</script>


<style>
body {
  display: flex;
  justify-content : center;
  align-items: center;
  min-height: 100vh;
}

.calculator {
  --button-width: 80px;
  --button-height: 80px;

  display: grid;
  grid-template-areas: "result result result result result result"
  "number-1 number-2 number-3 add equal TAN"
  "number-4 number-5 number-6 sub LN factorial"
  "number-7 number-8 number-9 mul SIN SQRT"
  "number-0 dot ac div COS power";
  grid-template-columns: repeat(6, var(--button-width));
  grid-template-rows: repeat(5, var(--button-height));

  box-shadow:  -8px -8px 16px -10px rgba(255,255,255,1),8px 8px 16px -10px rgba(0,0,0,.15);
  padding: 24px;
  border-radius: 20px;

  position:absolute;
  top: 150px;
  left: 400px;
}

.calculator button {
  margin: 8px;
  padding: 0;
  border: 0;
  display: block;
  outline: none;
  border-radius: calc(var(--button-height)/2);
  font-size: 24px;
  font-weight: normal;
  color: #999;
}

.calculator button:active{
  box-shadow: -4px -4px 10px -8px rgba(255,255,255,1) inset,4px 4px 10px -8px rgba(0,0,0,.3) inset;
}

.result {
  text-align: right;
  line-height: var(--button-height);
  font-size: 48px;
  padding: 0 20px;
  color: #666;
}

.select {
  position:absolute;
  top:150px;
  left:100px;

  display: grid;
  grid-template-areas: "select1"
  "select2";
  grid-template-columns: repeat(1, var(--button-width));
  grid-template-rows: repeat(2, var(--button-height));

}

.select1{
  margin: 8px;
  padding: 0;
  border: 0;
  outline: none;
  line-height: var(--button-height);
  font-size: 30px;
  font-weight: normal;
  padding: 0 20px;
  color: #666;
}

.select2{
  margin: 8px;
  padding: 0;
  border: 0;
  outline: none;
  line-height: var(--button-height);
  font-size: 30px;
  font-weight: normal;
  padding: 0 20px;
  color: #666;
}

.select button:active{
  box-shadow: -4px -4px 10px -8px rgba(255,255,255,1) inset,4px 4px 10px -8px rgba(0,0,0,.3) inset;
}

.history{
  position:absolute;
  top:150px;
  left:1100px;
}

.history th{
  margin: 8px;
  padding: 0;
  border: 0;
  outline: none;
  line-height: var(--button-height);
  font-size: 30px;
  font-weight: normal;
  padding: 0 20px;
  color: #666;
}

.history td{
  margin: 8px;
  padding: 0;
  border: 0;
  outline: none;
  line-height: var(--button-height);
  font-size: 30px;
  font-weight: normal;
  padding: 0 20px;
  color: #666;
}

.gethistory{
  position:absolute;
  top:100px;
  left:1100px;

  margin: 8px;
  padding: 0;
  border: 0;
  outline: none;
  line-height: var(--button-height);
  font-size: 30px;
  font-weight: normal;
  padding: 0 20px;
  color: #666;
}

.gethistory:active{
  box-shadow: -4px -4px 10px -8px rgba(255,255,255,1) inset,4px 4px 10px -8px rgba(0,0,0,.3) inset;
}

.intersetcalculator{
  display: grid;
  grid-template-areas: "time settime"
  "money setmoney"
  "restmoney getrestmoney"
  "borrowmoney getborrowmoney";
  grid-template-columns: repeat(2, var(--button-width));
  grid-template-rows: repeat(4, var(--button-height));

  position:absolute;
  top: 160px;
  left: 600px;
}

.time{
  text-align: right;
  line-height: var(--button-height);
  font-size: 72px;
  padding: 0 20px;
  color: #666;
}

.settime{
  text-align: right;
  line-height: var(--button-height);
  font-size: 24px;
  padding: 0 20px;
  color: #666;
}

.money{
  text-align: right;
  line-height: var(--button-height);
  font-size: 72px;
  padding: 0 20px;
  color: #666;
}

.setmoney{
  text-align: right;
  line-height: var(--button-height);
  font-size: 24px;
  padding: 0 20px;
  color: #666;
}

.restmoney{
  text-align: right;
  line-height: var(--button-height);
  font-size: 24px;
  padding: 0 20px;
  color: #666;
}

.borrowmoney{
  text-align: right;
  line-height: var(--button-height);
  font-size: 24px;
  padding: 0 20px;
  color: #666;
}

.getrestmoney{
  text-align: right;
  line-height: var(--button-height);
  font-size: 24px;
  padding: 0 20px;
  color: #666;
}

.getborrowmoney{
  text-align: right;
  line-height: var(--button-height);
  font-size: 24px;
  padding: 0 20px;
  color: #666;
}
</style>
