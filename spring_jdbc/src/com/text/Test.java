package com.text;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/*
 * 

@RunWith就是一个运行器 这个是指定使用的单元测试执行类，这里就指定的是SpringJUnit4ClassRunner.class；

@ContextConfiguration：这个指定spring配置文件所在的路径，可以同时指定多个文件；
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class Test {
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
/*	@org.junit.Test
	public void test() {
		//string 有内置连接池
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql:///test");
		dataSource.setUsername("root");
		dataSource.setPassword("123456");
		
		//创建模板类
		JdbcTemplate template = new JdbcTemplate();
		// 设置连接池
		template.setDataSource(dataSource);
		
		template.update("insert into t_account values(null,?,?)","zs",10000);
		
	}*/
	@org.junit.Test
	public void test1() {
		jdbcTemplate.update("insert into t_account values(null,?,?)","zs",10000);
		
	}
	@org.junit.Test
	public void test2() {
		jdbcTemplate.update("update  t_account set mony=? where name=?",200,"zs");
		
	}
	@org.junit.Test
	public void test3() {
		jdbcTemplate.update("delete from  t_account where name=?","zs");
		
	}
	//只能查询单行
	@org.junit.Test
	public void test4() {
		Account account =jdbcTemplate.queryForObject("select id,name,mony from t_account where name =?", new BeanMapper(),"zs");
		System.out.println(account);
	}
	//查询多行
	@org.junit.Test
	public void test5() {
		List<Account> list = new ArrayList<>();
		list =jdbcTemplate.query("select id,name,mony from t_account where name =?", new BeanMapper(),"ls");
		Iterator<Account> it =list.iterator();
		for(int i=0;i<list.size();i++){
			
			System.out.println(it.next());
		}
	}
}
class BeanMapper implements RowMapper<Account>{

//查到一个结果封装一次
	@Override
	public Account mapRow(ResultSet rs, int arg1) throws SQLException {
		Account ac = new Account();
		ac.setId(rs.getInt("id"));
		ac.setName(rs.getString("name"));
		ac.setMoney(rs.getDouble("mony"));
		
		
		return ac;
	}
}