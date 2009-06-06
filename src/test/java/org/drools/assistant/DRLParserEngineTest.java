package org.drools.assistant;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.drools.assistant.engine.DRLParserEngine;
import org.drools.assistant.info.drl.DRLRuleRefactorInfo;
import org.drools.assistant.info.drl.RuleBasicContentInfo;

public class DRLParserEngineTest extends TestCase {

	private String rule;
	private DRLParserEngine engine;
	private DRLRuleRefactorInfo info;

	@Override
	protected void setUp() throws Exception {
		rule = 	"package org.drools.assistant.test;\n\n" +
		"import org.drools.assistant.test.model.Company;\n" +
		"IMPORT org.drools.assistant.test.model.Employee;\n\n" +
		"import function org.drools.assistant.model.Class1.anotherFunction \n" +
		"import		function org.drools.assistant.model.Class1.mathFunction \n" +
		"global     org.drools.assistant.test.model.Class2    results \n"+
		"GLOBAL org.drools.assistant.test.model.Class3 current\n"+ 
		"expander help-expander.dsl\n" +
		"query \"all clients\"\n" +
		"	result : Clients()\n" +
		"end\n" +
		"query \"new query\"\n" +
		"	objects : Clients()\n" +
		"end\n" +
		"function String hello(String name) {\n"+
		"    return \"Hello \"+name+\"!\";\n"+
		"}\n" +
		"function String helloWithAge(String name, Integer age) {\n"+
		"    return \"Hello2 \"+name+\"! \" + age;\n"+
		"}\n" +
		"rule   \"My Test Rule\"\n" +
		"when\n"+ 
		"	$employee : Employee($age : age > 80, $company : company)\n" +
		"	$result : Company(company==$company, retireAge <= $age)\n" + 
		"then\n"+ 
		"	System.out.println(\"can retire\")\n" +
		"end\n";
		
		engine = new DRLParserEngine(rule);
	
	}
	
	public void testExecuteEngine() {
		info = (DRLRuleRefactorInfo) engine.parse();
		RuleBasicContentInfo content = info.getContentAt(123);
		Assert.assertEquals(true, content!=null);
		System.out.println(content.getType() + " -> " + content.getContent() + " from " + content.getOffset() + " to " + (content.getOffset()+content.getContentLength()));
	}
	
	public void testImport() {
		info = (DRLRuleRefactorInfo) engine.parse();
		RuleBasicContentInfo content = info.getContentAt(9);
		Assert.assertEquals(true, content!=null);
		System.out.println(content.getType() + " -> " + content.getContent() + " from " + content.getOffset() + " to " + (content.getOffset()+content.getContentLength()));
	}
	
	public void testNothingInteresting() {
		info = (DRLRuleRefactorInfo) engine.parse();
		RuleBasicContentInfo content = info.getContentAt(199);
		Assert.assertEquals(true, content==null);
	}
	
	public void testInsideTheRuleName() {
		info = (DRLRuleRefactorInfo) engine.parse();
		RuleBasicContentInfo content = info.getContentAt(670);
		Assert.assertEquals(true, content==null);
	}
	
	public void testInsideLHSRule() {
		info = (DRLRuleRefactorInfo) engine.parse();
		RuleBasicContentInfo content = info.getContentAt(790);
		Assert.assertEquals(true, content!=null);
		System.out.println(content.getType() + " -> " + content.getContent() + " from " + content.getOffset() + " to " + (content.getOffset()+content.getContentLength()));
	}
	
	public void testInsideRHSRule() {
		info = (DRLRuleRefactorInfo) engine.parse();
		RuleBasicContentInfo content = info.getContentAt(830);
		Assert.assertEquals(true, content!=null);
		System.out.println(content.getType() + " -> " + content.getContent() + " from " + content.getOffset() + " to " + (content.getOffset()+content.getContentLength()));
	}
	
}
