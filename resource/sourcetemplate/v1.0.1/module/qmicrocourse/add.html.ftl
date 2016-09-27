<div ng-controller="addController">
<div>
	<form name="addForm" ng-submit="doAdd(addForm)">
		<div class="title">${model.title}</div>
        <div class="hline"></div>
        <div>
        	
		   <#list model.fields as item>
		   
		   		<#if item.type =='daterangepicker' >
		        <div>
			        <span>${item.title}</span>
			        <div class="qinputgroup2" >
			            <input  <#if item.require == true> ng-required='true'</#if> qdaterangepicker class="form-control date-picker"  type="text" data-option="daterangeoption" ng-model="item.${item.name}"/>
			            <div class="addson calendar-bg"></div>
			        </div>
		        </div>
		        </#if>
		        <#if item.type =='text' >
		        <div>
			        <span>${item.title}</span>
			        <div class="qinputgroup2"  >
                            <input class="qinputitem2"  <#if item.require == true> ng-required='true'</#if>  class="qinputitem2"  type="text" ng-model="item.${item.name}"/>
                    </div>
		        </div>
		        </#if>
		        <#if item.type =='muliplyselect'  && item.typedata != ''  >
		        <div>
			        <span>${item.title}</span>
			        <div>
                        <div class="qinputgroup2"  <#if item.require == true> ng-required='true'</#if> 
                                     qdropdownselect data-initdata="item.${item.name}"  data-dropdowndata="${item.typedata}"  ng-model='selectitem.${item.name}'
                                     ng-change="item.${item.name}=selectitem.${item.name}.id;item['${item.name}_desc']=selectitem.${item.name}.name"
                                >
                                    <span  class="qinputitem2" >{{item['${item.name}_desc']}}</span>
                                    <div  class="addson dropdown-bg" style=""></div>
                        </div>
                    </div>
		        </div>
		        </#if>
		        <#if item.type =='muliplyselect'  && item.typedata == '' >
		        <div>
			        <span>${item.title}</span>
			        <div>
                        <div class="qinputgroup2"
                                     qdropdownselect data-initdata="item.${item.name}"  data-dropdowndata="selectitem.${item.parent}.sub"  ng-model='selectitem.${item.name}'
                                     ng-change="item.${item.name}=selectitem.${item.name}.id;item['${item.name}_desc']=selectitem.${item.name}.name"
                                >
                                    <span  class="qinputitem2" >{{item['${item.name}_desc']}}</span>
                                    <div  class="addson dropdown-bg" style=""></div>
                        </div>
                    </div>
		        </div>
		        </#if>
		        
		   </#list>
		   
		    <div style="clear:both;padding-top:20px;text-align:center;">
                <input  type="submit" class="actionbtn" value="添加" >
            </div>
       </div>
	</form>
</div>
</div>