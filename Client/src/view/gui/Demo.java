package view.gui;


public class Demo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MazeWindow w=new MazeWindow("gogo",1000,1000);
		w.run();
		/*Employee e=new Employee();
		e.setId(1);
		e.setName("bobo");
		BeanPropertiesSetter setter=new BeanPropertiesSetter(e.getClass());
		Employee e1=(Employee) setter.createWindow().cast(Employee.class);
		System.out.println(e1.age);*/
		/*
		Display display=new Display();
		Shell shell=new Shell();
		Employee e=new Employee();
		PropertiesDialog<Employee> prop=new PropertiesDialog(shell,e);
		prop.open();
		*/
		
	}

}
