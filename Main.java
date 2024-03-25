/* Class Name must be 'Main' */
import java.io.*;
import java.net.CookieHandler;
import java.util.*;
import java.util.Random;
import java.util.stream.Stream;

//===============================================================================
// main()
//===============================================================================
public class Main
{
    public static void main(String[] args) {
        //--------------------------------
        // AutoCheck(chk, trace)
        // chk: 1(자동 오류 체크), 0(키보드에서 직접 입력하여 프로그램 실행)
        // trace: true(오류발생한 곳 출력), false(단순히 O, X만 표시)
        //--------------------------------
        int chk = 1; if (chk != 0) new AutoCheck(chk, true).run(); else
            run(new Scanner(System.in));
        //       해당 객체를 아래 run() 함수의 인자로 넘겨 주어라. (null 대신에 넘겨 줄 것)
        //       위 기능을 반드시 하나의 문장으로 완성해야 한다. 변수 선언하지 말고 바로 넘겨 줌
        //       즉, run( Scanner 객체를 생성 ); 형태가 되어야 한다.
    }

    public static void run(Scanner scan) {
        // UI 클래스의 setScanner() 함수를 호출함; setScanner()가 static 함수라 이렇게 호출 가능함
        UI.setScanner(scan); // UI 클래스 내의 static 함수 호출
        MainMenu.run(); // MainMenu 클래스 내의 static 함수 호출방법: 클래스이름.함수이름();
        scan.close();
    }
}

//===============================================================================
// Main Menu
//===============================================================================
class MainMenu
{
    public static void run() {
        final int MENU_COUNT = 7;
        String menuStr =  // 7_3 수정
                "************* Main Menu **************\n" +
                        "* 0.exit 1.PersonManager 2.ch2 3.ch3 *\n" +
                        "* 4.ch5 5.PMbyMap 6.MyVectorTest     *\n" +
                        "**************************************\n";
        while(true){
            int menuItem = UI.selectMenu(menuStr, MENU_COUNT);
            //System.out.print("menu item: " + menuItem);
            switch(menuItem){
                case 1:
                    new MultiManager().run();
                    break;
                case 2:
                    Ch2.run();
                    break;
                case 3:
                    Ch3.run();
                    break;
                case 4:
                    new Inheritance().run();
                    break;
                case 5: new PMbyMap().run(); break; // 7_2
                case 6: new MyVectorTest().run(); break; // 7_3
            }
            if(menuItem == 0){
                break;
            }
        }

        System.out.println("\nGood bye!!");
    }
// 7-4를 하기 위한 임시 run()
//public static void run() {
//    new Memo("").run();
//}
//     6-2 를 하기 위한 임시 run()
//public static void run() {
//    var user = new Student("s1", 1, 65.4, true,  "Jongno-gu Seoul", "Physics", 3.8, 1);
//    Memo m = new Memo(user.getMemo());
//    m.run();
//    user.setMemo(m.toString());
//    System.out.println("\nGood bye!!");
//}
} // class MainMenu

interface BaseStation
{
    boolean connectTo(String caller, String callee);
    // 이 메소드는 Phone::sendCall(String caller)에서 호출되어야 한다.
    // callee라는 사람이 존재할 경우
    //     System.out.println("base station: sends a call signal of "+caller+
    //               " to "+callee)를 출력하고
    //     이 사람의 등록된 Phone의 receiveCall(caller, callee)을 호출하고 true를 리턴
    // 존재하지 않을 경우 "callee_name: NOT found"라는 에러 메시지 출력하고 false 리턴
}

interface Phone {
    void sendCall(String callee);
    // 이 메소드는 "made a call to 수신자_이름(callee)"라고 출력해야 하며 이 출력의 앞 또는 뒤에
    // 발신자 이름도 함께 출력하되 메이커가 알아서 적절히 회사명, 모델명 등과 함께 표시하면 된다.
    // 그런 후 baseStation.connectTo(caller, callee)를 호출해야 한다.
    void receiveCall(String caller);
    // 이 메소드는 "received a call from 송신자_이름(caller)"라고 출력해야 하며 이 출력의 앞 또는 뒤에
    // 수신자 이름도 함께 출력하되 메이커가 알아서 적절히 회사명, 모델명 등과 함께 표시하면 된다.
}

interface Calculator {
    // +, -, *, / 사칙연산만 지원하고 그 외의 연산자일 경우 "NOT supported operator" 에러 메시지 출력
    // 수식과 계산 결과 또는 에러 메시지를 출력해야 하며 이 출력의 앞 또는 뒤에
    // 계산기 소유주 이름도 함께 출력하되 메이커가 알아서 적절히 회사명, 모델명 등과 함께 표시하면 된다.
    void calculate(double oprd1, String op, double oprd2); // 예: (3, "+", 2.0)
    void calculate(String expr);                     // 예: ("3+2") ("2+ 3")

}

abstract class SmartPhone implements Phone, Calculator  { // TODO: 이 클래스는 Phone, Calculator를 구현한다.
    protected static BaseStation baseStation;
    protected static Calendar userDate = null; // ch6_1
    public static void setBaseStation(BaseStation bs) { baseStation = bs; }
    public static void setDate(String line) { // ch6_1
        if(line.isEmpty()) {
            userDate = null;
            return;
        }
        Scanner s = new Scanner(line);
        Calendar newCalendar = Calendar.getInstance();
        userDate = newCalendar;
        userDate.clear();


        userDate.set(s.nextInt(), s.nextInt()-1, s.nextInt(), s.nextInt(), s.nextInt(), s.nextInt());
        s.close();

    }

    protected String owner;  // 스마트폰 소유주 이름
    protected Calendar date; // ch6_1

    public SmartPhone(String owner) {
        this.owner = owner;
        date = (userDate == null)? Calendar.getInstance(): (Calendar)userDate.clone();  // ch6_1
    }
    public abstract String getMaker();
    public void setOwner(String owner) { this.owner = owner; }
    //    public void print() { System.out.print(owner+"'s Phone: "+getMaker()); }
//    public void println() { print(); System.out.println(); }
    @Override
    public String toString(){
        return owner+"'s Phone: "+getMaker() + " Phone (" +
                date.get(Calendar.YEAR) + "." + (date.get(Calendar.MONTH)+1) + "." + date.get(Calendar.DATE) + " " +
                (date.get(Calendar.AM) == Calendar.AM? "AM" : "PM")  + " " + date.get(Calendar.HOUR) + ":" + date.get(Calendar.MINUTE) + ":" +
                date.get(Calendar.SECOND) + ")";
    }
    public abstract SmartPhone clone();
}
class GalaxyPhone extends SmartPhone{ // TODO: 이 클래스는 SmartPhone 클래스를 상속한다.
    private void printTradeMark(String appName) {
        System.out.println(" @ "+owner+"'s Galaxy "+appName);
    }
    public GalaxyPhone(String owner) { super(owner); }

    // get, set
    @Override
    public void sendCall(String callee)        {
        System.out.print("made a call to "+callee); printTradeMark("Phone");
        baseStation.connectTo(owner, callee);
    }
    @Override
    public void receiveCall(String caller)     {
        System.out.print("received a call from "+caller); printTradeMark("Phone");
    }
    @Override
    public void calculate(double oprd1, String op, double oprd2) {
        System.out.print(oprd1+" "+op+" "+oprd2+" = ");
        switch (op) {
            case "+": System.out.print(oprd1 + oprd2); break;
            case "-": System.out.print(oprd1 - oprd2); break;
            case "*": System.out.print(oprd1 * oprd2); break;
            case "/": System.out.print(oprd1 / oprd2); break;
            default:  System.out.print("NOT supported operator"); break;
        }
        printTradeMark("Calculator");
    }
    @Override
    public void calculate(String expr){
        String oprs[] = { "+", "-", "*", "/" };
        int i;
        for (i = 0; i < oprs.length; i++)
            if (expr.indexOf(oprs[i]) >= 0) // expr에 oprs[i] 있는지 조사하고
                break;                      // 있으면 expr 내의 인덱스, 없으면 음수 반환
        if ((i >= oprs.length))             // expr에 적절한 연산자가 없을 경우
            calculate(0, expr, 0);          // 에러 처리를 위해 호출함
        else {
            String[] op = expr.split("\\"+oprs[i]);

            // 두 개의 피연산자(토큰)를 각각 double로 변환
            double op1 = Double.parseDouble(op[0].trim());
            double op2 = Double.parseDouble(op[1].trim());
            calculate(op1, oprs[i], op2);
        }
    }
    @Override
    public String getMaker() { return "SAMSUNG"; }

    @Override
    public SmartPhone clone() {
        return new GalaxyPhone(owner);
    }
}

class IPhone extends SmartPhone { // TODO: 이 클래스는 SmartPhone 클래스를 상속한다.
    String model;
    public IPhone(String owner, String model) { super(owner); this.model = model; };

    @Override
    public void sendCall(String callee)        {
        System.out.print(owner + "'s IPhone " + model +  ": " ); System.out.println("made a call to "+callee);
        baseStation.connectTo(owner, callee);
    }
    @Override
    public void receiveCall(String caller)     {
        System.out.print(owner + "'s IPhone " + model +  ": " ); System.out.println("received a call from "+caller);
    }

    private double add(double oprd1, double oprd2) { return oprd1 + oprd2; }
    private double sub(double oprd1, double oprd2) { return oprd1 - oprd2; }
    private double mul(double oprd1, double oprd2) { return oprd1 * oprd2; }
    private double div(double oprd1, double oprd2) { return oprd1 / oprd2; }

    @Override
    public void calculate(double oprd1, String op, double oprd2) {
        System.out.print(owner + "'s IPhone " + model +  ": " );
        switch (op) {
            case "+":
                System.out.println(oprd1+" "+op+" "+oprd2+" = " + add(oprd1, oprd2));
                break;
            case "-":
                System.out.println(oprd1+" "+op+" "+oprd2+" = " + sub(oprd1, oprd2));
                break;
            case "*":
                System.out.println(oprd1+" "+op+" "+oprd2+" = " + mul(oprd1, oprd2));
                break;
            case "/":
                System.out.println(oprd1+" "+op+" "+oprd2+" = " + div(oprd1, oprd2));
                break;
            default:  System.out.print(op + " = NOT supported operator"); break;
        }
    }
    @Override
    public void calculate(String expr){
        String oprs[] = { "+", "-", "*", "/" };
        int i;
        for (i = 0; i < oprs.length; i++)
            if (expr.indexOf(oprs[i]) >= 0) // expr에 oprs[i] 있는지 조사하고
                break;                      // 있으면 expr 내의 인덱스, 없으면 음수 반환
        if ((i >= oprs.length))             // expr에 적절한 연산자가 없을 경우
            calculate(0, expr, 0);          // 에러 처리를 위해 호출함
        else {
            int j = expr.indexOf(oprs[i]);
            String op1 = expr.substring(0, j);
            String opr = expr.substring(j,j+1);
            String op2 = expr.substring(j+1);

            expr = op1 + " " + opr + " " + op2;

            Scanner scanner = new Scanner(expr);
            calculate(scanner.nextDouble(), scanner.next(), scanner.nextDouble());
            scanner.close();
        }
                    /* TODO:
            expr.indexOf(oprs[i])를 이용해 expr 내에서 연산자의 위치(인덱스)를 구해 j에 저장
            String의 substring()을 이용해 expr 내에서 피연산자1, 연산자, 피연산자2 등 세 개의
            서브 문자열을 발췌하라. substring() 호출 시 인덱스 j를 활용하여 j의 바로 앞까지,
            j에서 j+1까지, j 바로 뒤에서 끝까지 세 개의 서브 문자열을 구할 수 있다.
            참고로 피연산자에는 공백 문자가 포함되어 있어도 괜찮다.
            피연산자1, 연산자, 피연산자2 서브 문자열들을 +를 이용하여 다시 하나의 문자열로 결합하여
            expr에 저장하라. 이때 피연산자와 연산자가 분리되게 중간에 " "를 무조건 추가하라.

            새로운 Scanner 변수 s를 만들어라. 이때 함수 인자로 새로 결합된 expr 문자열을 지정하라.
            [SmartPhone의 setDate(String line) 함수 참고]
            스캐너를 통해 실수값(피연산자1), 문자열(연산자), 실수값(피연산자2)를 읽어 들여라.
            (이 스캐너는 실수값과 문자열을 expr에서 읽어 들인다.)
            계산을 위해 기존 calculate(double, String, double)를 호출하라.
            스캐너를 닫아라.
            */
    }

    @Override
    public String getMaker() { return "Apple"; }

    public SmartPhone clone(){
        return new IPhone(owner, model);
    }
}

//===============================================================================
// class Person: ch4_1
//===============================================================================


class Person implements Comparable<Person>
{
    private String  name;    // 이름
    private int     id;      // Identifier
    private double  weight;  // 체중
    private boolean married; // 결혼여부
    private String  address; // 주소
    private String  passwd; // 암호
    private SmartPhone smartPhone;
    private String     memo;       // 메모: 6_2


    // 생성자 함수들
    public Person(String name, int id, double weight, boolean married, String address) {
        this(name,id,weight,married,address,null);
        //System.out.print("Person(): ");  //printMembers();  System.out.println();
    }

    public Person(String name){
        this(name, 0, 0.0, false, "");
    }

    public Person(Person p) { // 복사 생성자
        assign(p);
        //System.out.print("Person(p): ");  //printMembers();  System.out.println();
    }

    public Person(Scanner sc) { inputMembers(sc); }

    public Person(String name, int id, double weight,  // 5_3
                  boolean married, String address, SmartPhone smartPhone) {
        set(name, "", id, weight, married, address, smartPhone);
    }

    public void set(String name, String passwd, int id, double weight, boolean married, String address, SmartPhone smartPhone){
        this.name = name;
        this.passwd = passwd;
        this.id = id;
        this.weight = weight;
        this.married = married;
        this.address = address;
        setSmartPhone(smartPhone);
        memo = ""; // 6_2
    }


//    public void println() {
//        print(); System.out.println();
//    }

//    public void println(String msg) {
//        System.out.print(msg); print(); System.out.println();
//    }
    // assign() 함수

    // Getter: getXXX() 관련 함수들
    public String getName(){
        return this.name;
    }
    public int getId(){
        return this.id;
    }
    public double getWeight(){
        return this.weight;
    }
    public boolean getMarried(){
        return this.married;
    }
    public String getAddress(){
        return this.address;
    }
    public String getPasswd(){
        return this.passwd;
    }
    public SmartPhone getSmartPhone(){
        return this.smartPhone;
    }
    public Calculator getCalculator(){
        return this.smartPhone;
    }
    public Phone getPhone(){
        return this.smartPhone;
    }
    public String getMemo(){ return this.memo; }

    // Setter: overloading: set() 함수 중복
    public void set(String name){
        this.name = name; smartPhone.setOwner(name);
    }
    public void set(int id){
        this.id=id;
    }
    public void set(double weight){
        this.weight=weight;
    }
    public void set(boolean married){
        this.married= married;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public void setPasswd(String passwd){
        this.passwd = passwd;
    }
    public void setSmartPhone(SmartPhone smPhone) { // 5_3
        if (smPhone != null) {
            smartPhone = smPhone;
            smartPhone.setOwner(name);
        }
        else
            smartPhone = ((id % 2) == 1)? new GalaxyPhone(name) : // id가 홀수인 경우
                    new IPhone(name, "13"); // id가 짝수인 경우
    }
    public void setMemo(String m){this.memo = m;}

    // Candidates for virtual functions and overriding
    public void assign(Person p){
        set(p.name, p.passwd, p.id, p.weight, p.married, p.address, p.smartPhone.clone());
    }

    @Override
    public String toString(){
        return name+" "+id+" "+weight+" "+married+" :"+address+":";
    }


    // print(), clone(), whatAreYouDoing(), equals(), input() 함수
//    public void print() {
//        printMembers();
//    }

    public Person clone(){
        //System.out.println("Person::clone()");
        return new Person(this);
    }
    public void whatAreYouDoing(){
        System.out.println(name + " is taking a rest.");
    }

    @Override
    public boolean equals(Object o){
        Person p = (Person)o;
        return ((this.name.equals(p.name)) && (this.id == p.id));
        // 원래는 this.name == p.name 이였음
    }

    void input(Scanner sc) {
        inputMembers(sc);
    }

    public char getDelimChar() { return 'P'; } // 8_3

    private void inputMembers(Scanner sc){
        // TODO: 스캐너 s로부터 name, id, weight, married 멤버를 입력 받을 것
        name = sc.next();
        id = sc.nextInt();
        weight = sc.nextDouble();
        married = sc.nextBoolean();
        passwd = "";
        // 아래는 주소 필드를 입력 받는 부분이며 수정없이 그대로 사용하면 된다.
        // :로 시작하고 :로 끝나는 부분의 서브 문자열을 읽어 냄
        while ((address = sc.findInLine(":.*:")) == null)
            sc.nextLine();
        address = address.substring(1, address.length()-1);
        // 양쪽의 : :를 제거한 서브 문자열을 넘겨 받음
        set(name, "", id, weight, married, address, null); // 8_3
    }

    @Override
    public int compareTo(Person m) {
        return this.name.compareTo(m.name);
    }

//    private void printMembers() {
//        System.out.print(name+" "+id+" "+weight+" "+married+" :"+address+":");
//    }
}

class Student extends Person{ //
    private String department; // 학과
    private double GPA;        // 평균평점
    private int year;          // 학년


    public Student(String name, int id, double weight, boolean married, String address,
                   String department, double GPA, int year) {
        super(name, id, weight, married, address);
        // TODO: 수퍼(부모)클래스의 생성자를 호출하여 수퍼 클래스 멤버들을 초기화하라.
        set(department, GPA, year);
        //System.out.print("Student():"); //printMembers(); System.out.println();
    }

    public Student(Student s){
        super(s);
        set(s.department, s.GPA, s.year);
        //System.out.print("Student(s):"); printMembers(); System.out.println();
    }

    public Student(Scanner sc) {
        super(sc);
        inputMembers(sc);
    }

    // getter and setter
    public void set(String department, double GPA, int year){
        this.department = department;
        this.GPA = GPA;
        this.year = year;
    }

    public String getDepartment(){
        return department;
    }

    public Double getGPA(){
        return GPA;
    }

    public int getYear(){
        return year;
    }

    public void setDepartment(String department){
        this.department = department;
    }

    public void setGPA(double GPA){
        this.GPA = GPA;
    }

    public void setYear(int year){
        this.year = year;
    }
    // Overriding
//    @Override
//    public void print() {
//        super.print();
//        printMembers();
//    }

    @Override
    public String toString(){
        return super.toString() + " " + department+" "+GPA+" "+year;
    }
    @Override
    public boolean equals(Object o) {
        Student s = (Student)o;
        return (super.equals(s) && (this.department.equals(s.department)) && (this.year == s.year));
    }

    @Override
    public void whatAreYouDoing(){
        System.out.println("~~~~~~~~~~~~~~~~ Student::whatAreYouDoing() ~~~~~~~~~~~~~~~~");
        study();
        takeClass();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    @Override
    public Person clone(){
        //System.out.println("Student::clone()");
        return new Student(this);
    }
    @Override
    public void assign(Person p) {
        super.assign(p);
        Student s = (Student)p;
        set(s.department, s.GPA, s.year);
    }
    @Override
    void input(Scanner sc) {
        super.input(sc);
        inputMembers(sc);
    }



    // printMembers(), inputMembers(Scanner sc)
//    private void printMembers() {
//        System.out.print(" " + department+" "+GPA+" "+year);
//    }
    @Override
    public char getDelimChar() { return 'S'; } // 8_3

    private void inputMembers(Scanner sc){
        // TODO: 스캐너 s로부터 name, id, weight, married 멤버를 입력 받을 것
        department = sc.next();
        GPA = sc.nextDouble();
        year = sc.nextInt();
    }
    // 새로 추가된 메소드
    public void study() {
        System.out.println(getName()+" is studying as a "+year+"-year student in "+department);
    }
    public void takeClass() {
        System.out.println(getName()+" took several courses and got GPA "+GPA);
    }

}

class Worker extends Person{
    private String company;    // 회사명
    private String position;   // 직급

    public Worker(String name, int id, double weight, boolean married, String address,
                  String company, String position) {
        super(name, id, weight, married, address);
        // TODO: 수퍼(부모)클래스의 생성자를 호출하여 수퍼 클래스 멤버들을 초기화하라.
        set(company, position);
        //System.out.print("Worker():"); printMembers(); System.out.println();
    }

    public Worker(Worker w){
        super(w);
        set(w.company, w.position);
        //System.out.print("Worker(w):"); printMembers(); System.out.println();
    }

    public Worker(Scanner sc) {
        super(sc);
        inputMembers(sc);
    }

    // getter and setter
    public void set(String company, String position){
        this.company = company;
        this.position = position;
    }

    public void setCompany(String company){this.company = company;}
    public void setPosition(String position){this.position = position;}
    public String getCompany(){ return company; }
    public String getPosition(){ return position; }
    // Overriding
//    @Override
//    public void print() {
//        super.print();
//        printMembers();
//    }

    @Override
    public boolean equals(Object o) {
        Worker w = (Worker)o;
        return (super.equals(w) && (this.company.equals(w.company)) && (this.position.equals(w.position)));
    }

    @Override
    public void whatAreYouDoing(){
        System.out.println("!!!!!!!!!!!!!!!! Worker::whatAreYouDoing()!!!!!!!!!!!!!!!!!");
        work();
        goOnVacation();
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    @Override
    public Person clone(){
        //System.out.println("Worker::clone()");
        return new Worker(this);
    }

    @Override
    public void assign(Person p) {
        super.assign(p);
        Worker s = (Worker)p;
        set(s.company, s.position);
    }

    @Override
    void input(Scanner sc) {
        super.input(sc);
        inputMembers(sc);
    }

    // printMembers(), inputMembers(Scanner sc)
//    private void printMembers() {
//        System.out.print(" " + company+" "+position);
//    }

    @Override
    public String toString(){
        return super.toString() + " " + company + " " + position;
    }

    @Override
    public char getDelimChar() { return 'W'; } // 8_3

    private void inputMembers(Scanner sc){
        company = sc.next();
        position = sc.next();
    }
    // 새로 추가된 메소드
    public void work() {
        System.out.println(getName()+" works in "+company+" as "+position);
    }
    public void goOnVacation() {
        System.out.println(getName()+" is now enjoying his(her) vacation.");
    }
}
//===============================================================================
// User Interface
//===============================================================================
class UI
{
    public static boolean echo_input = false; // 자동오류체크 시 true로 설정됨
    public static Scanner scan;

    public static void setScanner(Scanner s) { scan = s; }

    // 사용자에게 메뉴("\n"+menuStr+"menu item? ")를 보여주고
    // 사용자가 선택한 메뉴항목의 인덱스(0 ~ (menuCount-1))를 리턴함
    // menuCount: 메뉴항목의 개수임
    public static int selectMenu(String menuStr, int menuCount) {
        int value = getIndex("\n"+menuStr+"menu item? ",menuCount);
        return value;
    }

    // 입력을 받기 위해 static Scanner scan 멤버를 활용하라. 즉, scan.함수() 형식으로 호출
    public static int getInt(String msg) {

        int value;

        while(true){
            try{
                System.out.print(msg);
                value = scan.nextInt();
                break;
            }
            catch(InputMismatchException e){
                System.out.println("Input an INTEGER. Try again!!");
            }
            scan.nextLine(); // 왜 여기에 nextLine() 이 들어가야 하는지 알기
        }
        //       입력 시 이 클래스의 scan 멤버 변수를 활용하라.
        //       (이 변수는 setScanner(Scanner s)에 의해 이미 초기화 되었음)

        if (echo_input) System.out.println(value); // 자동오류체크 시 입력 값을 출력함
        // 위 문장은 자동오류체크 시에 사용되는 문장임; 일반적으로 키보드로부터 입력받을 경우
        // 화면에 자동 echo되지만, 자동오류체크 시에는 입력파일에서 입력받은 값이 자동 echo 되지
        // 않으므로 명시적으로 출력 버퍼에 출력(echo) 해 주어야 한다.

        // (지시가 있을 때 구현할 것) 입력 버퍼에 남아 있는 '\n'를 제거하지 않으면 다음번 getLine()에서
        // '\n'만 빈 줄이 입력될 수 있으므로 입력 버퍼에 남아 있는 '\n'를 사전에 제거함
        scan.nextLine();
        return value;
    }

    // [0, (size-1)] 사이의 인덱스 값을 리턴함
    // 존재하지 않는 메뉴항목을 선택한 경우 에러 출력
    public static int getIndex(String msg, int size) {
        int value;
        while(true){
            value = getPosInt(msg);
            if (value > size-1){
                System.out.println(value + ": OUT of selection range(0 ~ " + (size-1) + ") Try again!!");
            }
            else{
                break;
            }
        }

        // TODO: 위 적절한 함수를 호출해 0 또는 양수를 입력 받은 후 적절하지 않은 인덱스(index)일 경우
        //       에러("index: OUT of selection range(0 ~ size-1) Try again!!")를
        //       출력하고 다시 입력 받는다.
        return value; // TODO: 입력 받은 값 리턴
    }

    // 0 또는 양의 정수 값을 입력 받아 리턴함
    public static int getPosInt(String msg) {
        int value;

        while(true){
            value = getInt(msg);
            if(value < 0){
                System.out.println("Input a positive INTEGER. Try again!!");
            }
            else{
                break;
            }
        }

        // TODO: 위 적절한 함수를 호출해 정수값을 입력 받은 후 입력된 값이 음수일 경우
        //       에러("Input a positive INTEGER. Try again!!") 출력하고 다시 입력 받는다.
        //       원하는 값이 입력될 때까지 위 과정을 계속 반복하여야 한다.
        return value; // TODO: 입력된 0 또는 양수 리턴
    }

    // 위 getInt()를 참고하여 msg를 화면에 출력한 후 문자열 단어 하나를 입력 받아 리턴함
    public static String getNext(String msg) {
        System.out.print(msg);

        String token = scan.next();

        // TODO: msg를 화면에 출력한 후 하나의 토큰(단어)을 입력 받아 변수 token에 저장함
        if (echo_input) System.out.println(token); // 자동오류체크 시 입력 값을 출력함
        scan.nextLine();
        return token;  // TODO: 입력 받은 한 단어를 리턴할 것
    }

    // msg를 화면에 출력한 후 하나의 행 전체를 입력 받아 리턴함
    public static String getNextLine(String msg) {
        System.out.print(msg);

        String line = scan.nextLine();
        // TODO: msg를 화면에 출력한 후 한 행 전체를 입력 받아 변수 line에 저장함
        if (echo_input) System.out.println(line); // 자동체크 시 출력됨

        return line;  // TODO: 입력 받은 한 행 전체를 리턴할 것
    }
}
class Memo
{
    private StringBuffer mStr;  // 메모를 저장하고 수정하기 위한 문자열 버퍼

    // 문자열 m을 이용하여 StringBuffer를 생성한다.
    public Memo(String m)    { mStr = new StringBuffer(m); }

    // StringBuffer mStr을 문자열로 변환하여 리턴한다.
    public String toString() { return mStr.toString(); }

    private String memoData =
            "ten ten ten ten ten ten ten ten ten ten\n" +
                    "eight eight eight eight eight eight eight eight\n" +
                    "EIGHT EIGHT EIGHT EIGHT EIGHT EIGHT EIGHT EIGHT\n" +
                    "seven seven seven seven seven seven seven\n" +
                    "six six six six six six\n" +
                    "five five five five five\n" +
                    "four four four four\n" +
                    "three three three\n" +
                    "- - - - - - - - - - - - - - - - - - - - -\n" +
                    "The Last of the Mohicans\n"+
                    "James Fenimore Cooper\n"+
                    "Author's Introduction\n"+
                    "It is believed that the scene of this tale, and most of the information \n"+
                    "necessary to understand its allusions, are rendered sufficiently \n"+
                    "obvious to the reader in the text itself, or in the accompanying notes.\n"+
                    "Still there is so much obscurity in the Indian traditions, and so much \n"+
                    "confusion in the Indian names, as to render some explanation useful.\n"+
                    "Few men exhibit greater diversity, or, if we may so express it, \n"+
                    "greater antithesis of character, \n"+
                    "than the native warrior of North America.";

    public void run() {
        String menuStr =  // ch7_3
                "+++++++++++++++++++++ Memo Management Menu +++++++++++++++++++\n"+
                        "+ 0.exit 1.display 2.find 3.findReplace 4.compare 5.dispByLn +\n"+
                        "+ 6.delLn 7.replLn 8.scrollUp 9.scrollDown 10.inputMemo      +\n"+
                        "+ 11.wordCount 12.countWordList                              +\n"+
                        "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n";

        // 멤버 mStr이 비었을 경우 위 memoData로 초기화한다.
        if (mStr.length() == 0) mStr.append(memoData);
        final int MENU_COUNT = 13; // 상수 정의

        while (true) {
            int menuItem = UI.selectMenu(menuStr, MENU_COUNT);
            switch(menuItem) {
                case 1: display();     break;
                case 2: find();        break;
                case 3: findReplace(); break;
                case 4: compare();     break;
                case 5: dispByLn();    break;
                case 6: delLn();       break;
                case 7: replLn();      break;
                case 8: scrollUp();    break;
                case 9: scrollDown();  break;
                case 10:inputMemo();   break;
                case 11:wordCount();     break; // ch7_3
                case 12:countWordList(); break; // ch7_3
                case 0:                return;
            }
        }
    }
    void display() { // Menu item 1
        System.out.println("------- Memo -------");
        System.out.print(mStr);
        if (mStr.length() > 0 && mStr.charAt(mStr.length()-1) != '\n')
            System.out.println();
        System.out.println("--------------------");
    }
    // content 문자열의 start 인덱스부터 word 문자열 단어를 찾아 그 단어의 시작 인덱스를 반환함
    // 찾지 못한 경우 -1를 반환함
    // " "로 분리된 word 단어만 찾고 다른 긴 단어 속에 포함된 경우에는 스킵한다.
    private int findWord(String content, String word, int start) {
        // content.indexOf(word, start):
        //    content 문자열의 start 인덱스 위치부터 word를 검색하고
        //    찾은 경우 찾은 시작 위치를, 못 찾은 경우 -1를 반환함
        //    word가 다른 긴 단어의 속에 포함되어 있어도 검색이 된다.
        if ((start = content.indexOf(word, start)) == -1)
            return -1; // 못 찾은 경우
        if (start > 0) {
            // 찾은 단어의 앞이 공백이 아닌 경우 (긴 단어 속에 포함되어 있는 경우임)
            //    찾은 단어 뒤쪽으로 계속 찾음; 재귀 함수
            if (!Character.isWhitespace(content.charAt(start-1)))
                return findWord(content, word, start+word.length());
        }
        // 찾은 단어가 content의 끝에 있을 경우
        if ((start+word.length()) == content.length())
            return start; // 찾은 경우
        // 찾은 단어의 뒤가 공백이 아닌 경우 (긴 단어 속에 포함되어 있는 경우임)
        //    찾은 단어 뒤쪽으로 계속 찾음; 재귀 함수
        if(!Character.isWhitespace(content.charAt(start+word.length())))
            return findWord(content, word, start+word.length());
        return start; // 찾은 경우
    }

    void find() { // Menu item 2
        String word = UI.getNext("word to find? ");
        // 전체 메모 mStr를 문자열로 변환한 후 이를 행 단위로 쪼갬
        // 아래 구분자 "\\v"은 행 단위로 쪼개라는 구분자를 의미함; 즉, '\n','\r','\f'등을 의미함
        String lines[] = mStr.toString().split("\\v");
        int tot_count = 0; // 단어를 찾은 총 횟수

        for(int i = 0; i < lines.length; i++){
            int count = 0; // lines[i]에서 단어 word를 찾은 횟수
            for(int j = 0; j < lines[i].length(); j++){
                j = findWord(lines[i], word, j);
                if(j != -1){
                    j+=word.length();
                    count++;
                    continue;
                }else{
                    break;
                }
            }if(count != 0){
                tot_count += count;
                System.out.println("[" + i + "] " + lines[i]);
            }
        }
        System.out.println("--------------------");
        System.out.println(tot_count + " words found");
    }

    private int findWord(StringBuffer content, String word, int start) {
        // content.indexOf(word, start):
        //    content 문자열의 start 인덱스 위치부터 word를 검색하고
        //    찾은 경우 찾은 시작 위치를, 못 찾은 경우 -1를 반환함
        //    word가 다른 긴 단어의 속에 포함되어 있어도 검색이 된다.
        if ((start = content.indexOf(word, start)) == -1)
            return -1; // 못 찾은 경우
        if (start > 0) {
            // 찾은 단어의 앞이 공백이 아닌 경우 (긴 단어 속에 포함되어 있는 경우임)
            //    찾은 단어 뒤쪽으로 계속 찾음; 재귀 함수
            if (!Character.isWhitespace(content.charAt(start-1)))
                return findWord(content, word, start+word.length());
        }
        // 찾은 단어가 content의 끝에 있을 경우
        if ((start+word.length()) == content.length())
            return start; // 찾은 경우
        // 찾은 단어의 뒤가 공백이 아닌 경우 (긴 단어 속에 포함되어 있는 경우임)
        //    찾은 단어 뒤쪽으로 계속 찾음; 재귀 함수
        if(!Character.isWhitespace(content.charAt(start+word.length())))
            return findWord(content, word, start+word.length());
        return start; // 찾은 경우
    }

    void findReplace() { // Menu item 3
        String find = UI.getNext("word to find? ");
        String repl = UI.getNext("word to replace? ");
        int count = 0; // 단어를 교체(찾은)한 횟수
        for(int i = 0; i < mStr.length(); i++){
            i = findWord(mStr, find, i);
            if(i != -1){
                mStr.replace(i, i + find.length(), repl);
                count++;
            }else{
                break;
            } i+=repl.length();
        }
        display();
        System.out.println(count + " words replaced");
    }

    void compare() { // Menu item 4
        String word = UI.getNext("word to compare? ");

        String str = mStr.toString();
        String tokens[] = str.split("\\s");
        int less = 0;
        int same = 0;
        int larger = 0;
        for(int i=0; i<tokens.length; i++){
            if(tokens[i].isEmpty()){
                continue;
            }
            if (word.compareTo(tokens[i]) == 0){
                same+=1;
            } else if (word.compareTo(tokens[i]) < 0) {
                larger+=1;
            } else if(word.compareTo(tokens[i]) > 0 ) {
                less+=1;
            }
        }
        System.out.println("less: " + less);
        System.out.println("same: " + same);
        System.out.println("larger: " + larger);

    }
    void dispByLn() { // Menu item 5
        System.out.println("--- Memo by line ---");
        if(mStr.length() != 0){
            String str = mStr.toString();
            String[] lines = str.split("\\v");
            for (int i = 0; lines.length > i; i++){
                System.out.println("[" + i + "] " + lines[i]);
            }
        }
        System.out.println("--------------------");
    }
    // 두 개의 정수 값(start, end)을 가지는 클래스
    // 함수에서 두 개의 값을 한꺼번에 리턴하고자 할 때 이 클래스의 객체를 생성하여 반환한다.
    // private 클래스이므로 이 클래스는 Memo 클래스 내에서만 사용가능하다.
    private class Pair {
        public int start, end;
        Pair(int s, int e) { start = s; end = e; }
    }
    // 행번호 lineNum(행은 0부터 시작)인 행의 시작 위치인 start와 (행의 끝+1)의 위치인 end를 찾아줌.
    // end는 사실 그 다음 행(lineNum+1)의 시작 위치이며 마지막 행인 경우 mStr.length()와 같다.
    // 해당 행 번호를 찾았으면 start, end 값을 가진 Pair 객체를, 찾지 못했으면 null을 반환
    private Pair find_line(int lineNum) {
        int start = 0, end = 0;

        for(int i=0; i<=lineNum; i++) {
            start=end;
            end=mStr.indexOf("\n", start);
            if(end==-1) {
                if(i==lineNum&&start<mStr.length()) {
                    end=mStr.length();
                }else {
                    return null;
                }
            }
            end++;
        }
        return new Pair(start, end);

//        for(int i=0; i<=lineNum; i++){
//            end = mStr.indexOf("\n", start);
//            // \n end 찾음 start = 0; end = 13;
//            if(end == -1 || start + 1 == mStr.length()) {
//                end = mStr.length();
//                return null;
//            }
//            if (i < lineNum)
//                start = end + 1;
//        }
//        return new Pair(start, end);

    }
    void delLn() { // Menu item 6
        int lineNum = UI.getPosInt("line number to delete? ");
        Pair p;
        if (mStr.length() == 0 || (p=find_line(lineNum)) == null)
            System.out.println("Out of line number range");
        else {
            mStr.delete(p.start, p.end);
            dispByLn();
        }

    }
    void replLn() { // Menu item 7
        int lineNum = UI.getPosInt("line number to replace? ");

        Pair p = find_line(lineNum);
        if (mStr.length() == 0 || (p=find_line(lineNum)) == null)
            System.out.println("Out of line number range");
        else {
            String replace = UI.getNextLine("input content to replace:");
            mStr.replace(p.start, p.end, replace+"\n");
            dispByLn();
        }
    }
    void scrollUp() { // Menu item 8
        if(mStr.length() == 0){
            dispByLn();
            return;
        }
//        if(mStr.lastIndexOf("\n") == -1){
//            mStr.append("\n");
//        }
        if (mStr.charAt(mStr.length() - 1) != '\n') {
            mStr.append("\n");
        }
        Pair p = find_line(0);
        String sub = mStr.substring(p.start, p.end);
        mStr.append(sub);
        mStr.delete(p.start, p.end);
        dispByLn();
    }
    // 마지막 행의 시작 위치를 구하여 반환한다.
    private int find_last_line() {
        int start = 0, pos;

        for(int i=0; i<=mStr.length(); i++){
            pos = mStr.indexOf("\n", start);
            if(pos == -1 || pos+1 == mStr.length()) {
                break;
            }else{
                start = pos + 1;
//                System.out.println(start);
            }
        }
        return start;
    }
    void scrollDown() { // Menu item 9
        if(mStr.length() == 0){
            dispByLn();
            return;
        }
        if (mStr.charAt(mStr.length() - 1) != '\n') {
            mStr.append("\n");
        }
        int first = find_last_line();
        String sub = mStr.substring(first, mStr.length());
        mStr.delete(first, mStr.length());
        mStr.insert(0, sub);
        dispByLn();
    }
    /*
    In war, he is daring, boastful, cunning, ruthless, self-denying,
    and self-devoted; in peace, just, generous, hospitable, revengeful,
    superstitious, modest, and commonly chaste.
    These are qualities, it is true, which do not distinguish all alike;
    but they are so far the predominating traits of these remarkable people
    as to be characteristic.
    It is generally believed that the Aborigines of the American continent
    have an Asiatic origin.
    */
    void inputMemo() { // Menu item 10
        mStr.setLength(0);
        System.out.println("--- input memo lines, and then input empty line at the end ---");
        while(true){
            String line = UI.getNextLine("");
            // 왜 스캐너로 새로 받으면 오류가 나는지 모르겟음
            if (line.isEmpty())
                break;
            mStr.append(line).append("\n");
        }

    }
    private Map< String, Integer > getWordCountMap() { // ch7_3
        String str = mStr.toString();
        String words[] = str.split("\\s");
        TreeMap<String, Integer> wordCountMap = new TreeMap<String, Integer>();

        for(String w : words){
            if(w.isEmpty())
                continue;

            Integer count = wordCountMap.get(w);

            if(count == null)
                wordCountMap.put(w,1);
            else
                wordCountMap.put(w,count+1);

        }
        return wordCountMap; // Map으로 자동 업 케스팅되어 리턴됨
    }
    void wordCount() { // Menu item 11
        var wordCountMap = getWordCountMap();
        System.out.println("----------------");
        System.out.println("Word      Count");
        System.out.println("----------------");

        Set<Map.Entry<String, Integer>> wcEntries = wordCountMap.entrySet();

        for(var wc : wcEntries){
            String word = wc.getKey();
            int count = wc.getValue();
            if (count > 1)  // %-7s: 문자열을 7 칸 안에 출력하되 좌 맞춤
                System.out.printf("%-7s    %2d\n", word, count);
        }
        System.out.println("----------------");
    }
    void countWordList() {
        var wordCountMap = getWordCountMap();

        Set<Map.Entry<String, Integer>> wcEntries = wordCountMap.entrySet();

        TreeMap<Integer, Vector<String>> countWordsMap = new TreeMap<>();

        for(var wc : wcEntries){
            String word = wc.getKey();
            int count = wc.getValue();

            Vector<String> wordVct = countWordsMap.get(count);
            if(wordVct == null){
                wordVct = new Vector<>();
                countWordsMap.put(count, wordVct);
            }
            wordVct.add(word);

        }
        /* ToDo: 먼저 구현한 wordCount()을 참고하여 아래를 구현하라.
        1) 위 wordCountMap의 엔트리 집합을 구해서 wcEntries에 저장하라.
        2) 키는 (단어의 출현 회수)이고, 값은 (동일한 출현 횟수를 가진 단어들을 저장할 수 있는 Vector)인
           Map을 생성하여 countWordsMap에 저장한다.
           즉, 이 맵의 엔트리는 < Integer, Vector< String > > 이다.
           이때 생성할 맵은 출현 횟수별로 정렬이 되어 있는 맵을 선택해야 한다.(작은 수에 큰 수로 정렬)
        3) for-each 문을 이용하여 엔트리 집합 wcEntries의 각 엔트리 wc에 대해
               wc에서 키인 word와 값인 count를 구한다.
               count를 키로 사용해서 countWordsMap의 값인 wordVct 벡터를 검색한다.
               만약 검색에 실패 했다면 (처음 삽입 시)
                   문자열 백터를 새로 생성하여 wordVct에 저장한다.
                   wordVct를 countWordsMap에 삽입한다.
               wordVct에 word를 삽입한다.
        */
        System.out.println("----------------");
        System.out.println("Count  Words");
        System.out.println("----------------");

        // 아래는 키의 순서를 역순으로 재배치한 새로운 맵 reverseCWMap을 생성한다.
        // 이 맵은 단어의 출현 횟수가 큰 수부터 작은 수 순서로 횟수를 저장하고 있다.
        var reverseCWMap = countWordsMap.descendingMap();

        Set<Map.Entry<Integer, Vector<String>>> cwEnties = reverseCWMap.entrySet();
        for(var cw : cwEnties){
            int count = cw.getKey();

            if(count == 1)
                continue;
            System.out.printf("%2d     ", count);
            Vector<String> wordVct = cw.getValue();

            for(var w : wordVct){
                System.out.print(w+ " ");
            }
            System.out.println();
        }
        /* ToDo:

        1) 위 reverseCWMap의 엔트리 집합을 구해서 cwEntries에 저장하라.
        2) for-each 문을 이용하여 엔트리 집합 cwEntries의 각 엔트리 cw에 대해
               cw에서 키인 count를 구한다.
               count가 1이면 스킵한다.
               System.out.printf("%2d     ", count);
               cw에서 값인 wordVct 벡터를 구한다.
               for-each 문을 이용하여 벡터 wordVct의 각 원소 w에 대해
                   System.out.print(w+ " ");
               System.out.println();
        */
        System.out.println("----------------");
    }
}   // Memo class: ch6_2

//===============================================================================
// CurrentUser class: ch4_1
//===============================================================================
class CurrentUser
{
    Person user;

    CurrentUser(Person user) {
        this.user = user;
    }

    public void run() {
        String menuStr =
                "++++++++++++++++++++++ Current User Menu ++++++++++++++++++++++++\n" +
                        "+ 0.logout 1.display 2.getter 3.setter 4.copy 5.whatAreYouDoing +\n" +
                        "+ 6.isSame 7.update 8.chPasswd(4_2) 9.chSmartPhone(5_3)         +\n" +
                        "+ 10.clone(5_3) 11.calc(5_3) 12.phoneCall(5_3) 13.chWeight(6_1) +\n" +
                        "+ 14.calcString(6_2) 15.memo(6_2)                               +\n" +
                        "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n";
        final int MENU_COUNT = 16; // 상수 정의
        while (true) {
            int menuItem = UI.selectMenu(menuStr, MENU_COUNT);
            switch(menuItem) {
                case 1: display();         break;
                case 2: getter();          break;
                case 3: setter();          break;
                case 4: copy();            break;
                case 5: whatAreYouDoing(); break;
                case 6: equals();          break;
                case 7: update();          break;
                case 8: chPasswd();        break;
                case 9: chSmartPhone();    break;
                case 10:userClone();       break;
                case 11:calc();            break;
                case 12:phoneCall();       break;
                case 13:chWeight();        break;
                case 14:calcString();      break;
                case 15:memo();            break;
                case 0:                    return;
            }
        }
    }
    void display() {

//        user.println();
//        user.getSmartPhone().println();

        System.out.println(user);
        System.out.println(user.getSmartPhone().toString());
    } // Menu item 1

    void getter() { // Menu item 2
        System.out.println("name:" + user.getName() + ", id:" + user.getId() + ", weight:" +
                user.getWeight() + ", married:" + user.getMarried() +
                ", address:" + user.getAddress());
    }
    void setter() { // Menu item 3
        var p = new Person("p");
        p.set(p.getName());
        p.set(user.getId());
        p.set(user.getWeight());
        p.set(user.getMarried());
        p.setAddress(user.getAddress());
        System.out.println("p.set(): " + p);
    }
    void copy() { // Menu item 4
        System.out.println("user: " + user);
        var p = user.clone();
        System.out.println("p: " + p);
    }
    void whatAreYouDoing() {  // Menu item 5
        user.whatAreYouDoing();
    }
    void equals() { // Menu item 6
        System.out.println("user: " + user);
        var p = new Person("user"); p.set(1);
        System.out.println("p: " + p);
        System.out.println("p.equals(user): " + p.equals(user));
        p.assign(user);
        System.out.println("p.assign(user): " + p);
        System.out.println("p.equals(user): " + p.equals(user));
    }
    void update() { // Menu item 7
        System.out.println("input person information:");
        user.input(UI.scan); // user 100 65 true :426 hakdong-ro, Gangnam-gu, Seoul:
        if (UI.echo_input) System.out.println(user); // 자동오류체크시 출력됨
        display();
    }
    void chPasswd() {
        String passwd = UI.getNext("new password: ");
        user.setPasswd(passwd);
        System.out.println("password changed");
    }
    void chSmartPhone() { // Menu item 9
        SmartPhone phone;
        //        TODO: 사용자가 입력한 maker를 참고하여 적절한 스마트폰을 생성하여 교체한다.
        //        이때 스마트폰의 소유주는 사용자의 이름으로 설정하고 IPhone의 경우 모델을 "14"로 하라.
        //        여기서 직접 스마트폰 객체를 생성한 후 setSmartPhone(..)을 호출하여 교체하라.
        //                메이커를 잘못 입력한 경우 아래 display()를 호출하지 않게 바로 리턴하라.
        while (true) {
            String maker = UI.getNext("maker of phone to change(ex: Samsung or Apple)? ");
            if (maker.equals("Samsung")) {
                phone = new GalaxyPhone(user.getName());
            } else if (maker.equals("Apple")) {
                phone = new IPhone(user.getName(), "14");
            } else {
                System.out.println(maker + ": WRONG phone's maker");
                return;
            }
            user.setSmartPhone(phone);
            display();
            return;
        }
    }
    void userClone() { // Menu item 10
        display();
        Person c = user.clone();
        System.out.println("------------------\nclone:");
        System.out.println(c);
        System.out.println(c.getSmartPhone().toString());
        System.out.println("\nchange clone's name "+c.getName()+" to c1\n");
        c.set("c1"); // clone의 이름을 c1으로 변경함: 스마트폰의 소유주도 c1으로 변경됨
        display();
        System.out.println("------------------\nclone:");
        System.out.println(c);
        System.out.println(c.getSmartPhone().toString());
    }
    void calc() { // Menu item 11: 연산자와 피연산자는 스페이스로 분리되어 있어야 함
        //TODO: "expression: "을 출력하고 연산자와 두개의 피연산자를 스캐너로부터 입력 받아라.
        System.out.print("expression: ");
        Double d1 = UI.scan.nextDouble();
        String op = UI.scan.next();
        Double d2 = UI.scan.nextDouble();

        if (UI.echo_input) System.out.println(d1+" "+op+" "+d2); // 자동오류체크시 출력됨
        user.getCalculator().calculate(d1, op, d2);
    }
    void phoneCall() { // Menu item 12
        // PersonManager에 등록되어 있는 사용자 중 한명의 이름을 입력하라.
        String callee = UI.getNext("name to call? ");
        user.getPhone().sendCall(callee);
    }
    void chWeight() { // Menu item 13
        double weight = user.getWeight();
        System.out.println("weight:" + user.getWeight() + " sqrt:" + Math.sqrt(user.getWeight()) + " ceil:" + Math.ceil(Math.sqrt(user.getWeight())) +
                " floor:" + Math.floor(Math.sqrt(user.getWeight())) + " round:" + Math.round(Math.sqrt(user.getWeight())));

        user.set(Math.ceil(Math.sqrt(user.getWeight())) * Math.floor(Math.sqrt(user.getWeight())));
        System.out.println(user.toString());
    }
    // Menu item 14:ch6_2: "2+3", "2+ 3"처럼 연산자와 피연산자가 붙어 있어도 됨
    void calcString() {
        String line = UI.getNextLine("expression: ");
        user.getCalculator().calculate(line);
    }
    void memo() { // Menu item 15: ch6_2
        Memo m = new Memo(user.getMemo());
        m.run();
        user.setMemo(m.toString());
    }

} // CurrentUser class: ch4_1
//===============================================================================

class Factory
{
    static public void printInputNotice(String preMsg, String postMsg) {
        System.out.println("input"+preMsg+" [delimiter(P,S,or W)]"+
                " [person information]"+postMsg+":");
    }
    static public Person inputPerson(Scanner sc) {
        Person p = null;
        String delimiter = sc.next();
        switch (delimiter) {
            case "S": p = new Student(sc); break;
            case "W": p = new Worker (sc); break;
            case "P": p = new Person (sc); break;
            default :
                String nextLn = sc.nextLine();
                if (UI.echo_input) System.out.println(delimiter+nextLn);
                System.out.println(delimiter+": WRONG delimiter");
                return null;
        }
        if (UI.echo_input) System.out.println(delimiter.equals("") ? "" : delimiter+" " + p);
        return p;
    }
}   // Factory class: ch4_2

class PersonManager implements BaseStation
{
    int cpCount;
    //private VectorPerson pVector;
    private Vector< Person > pVector;
    //private MyVector pVector;
    //private Factory factory;
    private Person array[];
    private Random rand; // 7_1 추가

    public PersonManager(Person array[]) {
        //System.out.println("PersonManager(array[])");
        cpCount = 0;                     // 7_1 추가
        rand = new Random(0);  // 0: seed 값임, 7_1 추가
        pVector = new Vector<>();
        //pVector = new MyVector< Person >();
        //this.factory = factory;
        this.array = array;
        SmartPhone.setBaseStation(this);
        addArray();
        display();
    }

    public void run() {
        String menuStr =
                "=================== Person Management Menu =====================\n" +
                        "= 0.exit 1.display 2.clear 3.reset 4.remove 5.copy 6.append    =\n" +
                        "= 7.insert 8.login 9.dispStudent(5_3) 10.dispPhone(5_3)        =\n" +
                        "= 11.find(6_1) 12.wrapper(6_1) 13.shuffle(6_1) 14.setDate(6_1) =\n" +
                        "= 15.chAddress(6_2) 16.collections(7_1) 17.fileManager(8_1)    =\n" +
                        "================================================================\n";
        final int MENU_COUNT = 18; // 상수 정의
        while (true) {
            int menuItem = UI.selectMenu(menuStr, MENU_COUNT);
            switch(menuItem) {
                case 1: display();         break;
                case 2: clear();           break;
                case 3: reset();           break;
                case 4: remove();          break;
                case 5: copy();            break;
                case 6: append();          break;
                case 7: insert();          break;
                case 8: login();           break;
                case 9: dispStudent();     break;
                case 10:dispPhone();       break;
                case 11:find();			   break;
                case 12:wrapper();         break;
                case 13:shuffle();         break;
                case 14:setDate();         break;
                case 15:chAddress();       break;
                case 16:collections();     break;
                case 17:fileManager();     break;   // ch8_1
                case 0:                    return;
            }
        }
    }
    public static void display(List< Person > list) {
        int count = list.size(); // ToDo: pVector의 모든 원소의 개수
        //System.out.println("display(): count " + count);
        for (int i = 0; i < count; ++i) {
            System.out.print("[" + i + "] ");
            System.out.println(list.get(i).toString());
//            System.out.println("[" + i + "] " + pVector.get(i).toString());
//            System.out.println("empty():" + pVector.isEmpty() + ", size():" + pVector.size()
//                    + ", capacity():" + pVector.capacity());
        }
    }

    public void display() { // Menu item 1
        display(pVector);
    }
    public void clear() {  // Menu item 2
        pVector.clear(); // ToDo: pVector의 모든 원소를 삭제하라.
        display();
    }
    public void reset() { // Menu item 3
        pVector.clear(); // ToDo: pVector의 모든 원소를 삭제하라.
        addArray();
        display();
    }
    public void remove() { // Menu item 4
        if (pVector.isEmpty()) { // ToDo: pVector의 원소가 하나도 없을 경우
            System.out.println("no entry to remove");
            return;
        }
        int index = UI.getIndex("index to delete? ", pVector.size());
        pVector.remove(index); // ToDo: pVector의 index 원소를 삭제하라.
        display();
    }

    public void copy() { // Menu item 5
        cpCount++;
        // ToDo: pVector의 각각의 원소 인덱스 i에 대해
        for (int i = 0, size = pVector.size(); i < size; ++i) {
            Person p = pVector.get(i).clone(); // ToDo: pVector의 i번째 원소를 복제해서 p에 저장하라.
            String name = p.getName();
            for (int j = 0; j < cpCount; ++j)
                name = name.charAt(0)+name;
            p.set(name);
            p.set(p.getId() + 20 * cpCount);
            p.set(p.getWeight() + cpCount);
            if (cpCount % 2 == 1)
                p.set(!p.getMarried());
            pVector.add(p); // ToDo: p를 pVector의 맨 뒤에 추가하라.
        }
        display();
    }
    public void append() { // Menu item 6
        int count = UI.getPosInt("number of persons to append? ");
        Factory.printInputNotice(" "+Integer.toString(count), " to append");
        for (int i = 0; i < count; ++i) {
            Person p = Factory.inputPerson(UI.scan);
            if(p != null) {
                pVector.add(p);
            }else{
                --i;
            }
        }
        display();
    }
    public void insert() { // Menu item 7
        int index = 0;
        if (pVector.size() > 0) {
            // ToDo: 새로운 원소를 삽입할 장소는 pVector의
            //       인덱스 0에서부터 마지막 원소 바로 다음 장소까지 삽입 가능하다.
            index = UI.getIndex("index to insert in front? ", pVector.size()+1);
            if (index < 0) return;
        }
        Factory.printInputNotice("", " to insert");
        Person p = Factory.inputPerson(UI.scan);
        if(p == null) return; // ToDo: 객체 p가 잘못 입력된 객체인 경우 여기서 리턴하라.
        pVector.add(index, p); // ToDo: 객체 p를 pVector의 index 위치에 삽입하라.
        display();
    }
    private void addArray() {
        for(int i = 0; i<array.length; i++){
            Person p = array[i].clone();
            pVector.add(p.clone());
        }
        // ToDo: array의 모든 원소를 순서적으로 복사한 후 pVector에 추가하라.
    }
    // 사용자로부터 VectorPerson pVector에 저장된 사람들 중에서 로그인할 사람의 이름(name)과 비번을 입력받고
    // 해당 비번이 맞으면 CurrentUser의 객체를 생성하고 이 객체의 run() 멤버 함수를 호출한다.
    // 초기 비번은 설정되어 있지 않기에 그냥 엔터치면 된다.
    public void login() {  // Menu item 8
        String name = UI.getNext("user name: ");
        Person p = findByName(name);
        if (p == null) return;
        String passwd = UI.getNextLine("password: ");
        //passwd.strip();
        if (passwd.equals(p.getPasswd()))
            new CurrentUser(p).run();
        else
            System.out.println("WRONG password!!");
    }
    public void dispStudent() { // Menu item 9: ch5_3
        int count = pVector.size();
        System.out.println("dispStudent():");
        for(int i=0; i<count; i++){
            if(pVector.get(i) instanceof Student){
                System.out.print("[" + i + "] "); System.out.println(pVector.get(i).toString());
            }
        }

    }
    public void dispPhone() { // Menu item 10: ch5_3
        int count = pVector.size();
        System.out.println("dispPhones(): count " + count);
        for (int i = 0; i < count; ++i) {
            System.out.print("[" + i + "] "); System.out.println(pVector.get(i).getSmartPhone().toString());
        }
    }

    public void find() { // Menu item 11: ch6_1
        boolean found = false;

        Factory.printInputNotice("", " to find by equals()");
        Person p = Factory.inputPerson(UI.scan); // 한 사람의 정보를 입력 받음
        if ( p == null) return;

        for(int i=0; i < pVector.size(); i++) {
            if (p.getClass() == pVector.get(i).getClass()) {
                if (p.equals(pVector.get(i))) {
                    System.out.print("[" + i + "] ");
                    System.out.println(pVector.get(i).toString());
                    return;
                }
            }
        }
        System.out.println("NOT found by equals()");
    }

    void wrapper() { // Menu item 12: ch6_1
        for(int i=0; i<pVector.size(); i++){
            System.out.print("[" + i + "] ");
            Person p = pVector.get(i);
            dispPersonInfo(p.getName(), Integer.toString(p.getId()),
                    Double.toString(p.getWeight()), Boolean.toString(p.getMarried()));
        }
    }
    public void shuffle() { // Menu item 13: ch6_1
        for(int i=0; i<pVector.size(); i++){
            //int j = (int)(Math.random() * pVector.size()-1);
            int j = rand.nextInt(pVector.size()); // [0 ~ (pVector.size()-1)]
            pVector.set(j, pVector.set(i, pVector.get(j)));
        }

        display();
    }
    // setDate() 후, copy()하면 SmartPhone에 바뀐 날짜가 적용됨;
    // setDate()시 그냥 엔터하면 현재시간이 설정됨
    public void setDate() { // Menu item 14: ch6_1
        String line = UI.getNextLine("date and time(ex: 2021 10 1 18 24 30)? ");
        SmartPhone.setDate(line);
    }

    void chAddress() { // Menu item 14: ch6_2
        for (int i = 0; i < pVector.size(); ++i) {
            Person p = pVector.get(i);
            p.setAddress(newAddress(p.getAddress()));
                /* TODO:
                p의 주소를 얻어와 아래 newAddress(String)를 호출하여
                수정한 주소를 얻어 온 후 이를 다시 p의 주소로 설정하라. (한 줄로 완성해 보라)
                */
        }
        display();
    }
    public void collections() { // Menu item 16: ch7_1
        new CollectionsByList(pVector).run();
    }
    public void fileManager() {
        new FileManager(pVector).run();
    }

    @Override
    public boolean connectTo(String caller, String callee) {
        Person p = findByName(callee);
        if(p == null) return false;

        System.out.println("base station: sends a call signal of "+caller+ " to "+callee);
        p.getPhone().receiveCall(caller);
        return true;
    }

    // pVector에 삽입되어 있는 Person 객체들 중 사용자가 입력한 이름 name과
    // 동일한 이름을 가진 객체를 찾아 리턴한다.
    private Person findByName(String name) {
        int i, count = pVector.size();
        for (i = 0; i < count; ++i)
            if (name.equals(pVector.get(i).getName()))
                return pVector.get(i);
        System.out.println(name + ": NOT found");
        return null;
    }

    private void dispPersonInfo(String sname, String sid, String sweight, String smarried) {

        char first = sname.charAt(0);
        char last = sname.charAt(sname.length()-1);

        sname = last + sname.substring(1, sname.length()-1) + first;

        if(sid.charAt(sid.length()-1) == '0'){
            sid = sid.substring(0,sid.length()-1) + '1';
        }
        boolean married = Boolean.parseBoolean(smarried);
        married = !married;

        dispPersonInfo(sname, Integer.parseInt(sid), Double.parseDouble(sweight), married);
                /*
        TODO:
        sname의 첫 글자와 끝 글자를 서로 바꾸어라. 예를 들어 "Park" -> "karP"
        sid의 끝 글자가 '0'이면 '1'로 변환하라. "2340" -> "2341"
        dispPersonInfo(String, int, double, boolean)를 호출하라.
        이때 필요한 매개변수는 int, double, boolean 으로 변환한 후 호출해야 한다.
        변환한 후 married의 경우 true는 false로, false는 true로 바꾼 후 호출하라.
        */
    }
    private void dispPersonInfo(String sname, int id, double weight, boolean married) {
        System.out.println(sname + " " + id + " 0x" + Integer.toHexString(id) + " 0" + Integer.toOctalString(id) +
                " 0b" + Integer.toBinaryString(id) + " " + weight + " " + married);
    }
    private String newAddress(String address) {

        address = address.toLowerCase();
        address = address.replace("-gu", "_gu");

        String s[] = address.split(",");
        address = "";

        for(String token : s){
            token = token.trim();
            address += token + " ";
        }
        return address.trim();
        /*
        TODO:
        address를 모두 소문자로 변경하라.
        address에서 "-gu"를 "_gu"로 교체하라.

        address에서 ","의 앞뒤 공백(','를 포함하여)을 하나의 공백문자 " "로 교체한
        새로운 address 문자열을 만든 다음 이를 반환하라. (아래 힌트 참고)

        힌트: address에서 ","를 구분자로 사용하여 여러 개의 토큰으로 잘라라. split() 사용
             address = "";
             for 문: 각 토큰의 양쪽 끝쪽에 있는 공백(스페이스, 탭, 엔터) 문자를 모두 제거하라.
             for 문: 각 토큰을 address의 뒤에 추가하여(+) 새로운 address를 만들고
                    그 뒤에 매번 " "를 추가(+)하라. (가능하면 두 개의 for를 하나로 만들어 보라)
             새로 완성된 address를 반환하라.
        */
    }
}   // ch4_2: PersonManager class

// class MultiManager: ch4_1
//===============================================================================
class MultiManager
{
    private Person persons[] = {
            new Person("p0", 10, 70.0, false, "Gwangju ,Nam-gu , Bongseon-dong 21"),
            new Person("p1", 11, 61.1, true,  "Jong-ro 1-gil,Jongno-gu,   Seoul"),
            new Person("p2", 12, 52.2, false, "1001, Jungang-daero, Yeonje-gu, Busan"),
            new Person("p3", 13, 83.3, true,  "100 Dunsan-ro Seo-gu Daejeon"),
            new Person("p4", 14, 64.4, false, "88 Gongpyeong-ro, Jung-gu, Daegu"),
    };
    // new를 이용해 동적으로 할당할 경우 소멸자 함수를 만들어 거기서 delete 해 주어야 함

    private Student students[] = {
            new Student("s1", 1, 65.4, true,  "Jongno-gu Seoul", "Physics", 3.8, 1),
            new Student("s2", 2, 54.3, false, "Yeonje-gu Busan", "Electronics", 2.5, 4),
    };

    private Worker workers[] = {
            new Worker("w1", 3, 33.3, false, "Kangnam-gu Seoul",  "Samsung", "Director"),
            new Worker("w2", 4, 44.4, true,  "Dobong-gu Kwangju", "Hyundai", "Manager"),
    };

    private Person allPersons[] = {
            persons[0], persons[1], students[0], students[1], workers[0], workers[1],
    };

    public void run() {
        //System.out.println("PersonManager::run() starts");
        var pm = new PersonManager(allPersons);
        pm.run();
        //System.out.println("PersonManager::run() returned");
    }
} // class MultiManager: ch4_1

// 아래 클래스를 기존 MultiManager 클래스 뒤에 배치하라.
// 이 클래스는 기존 PersonManager를 축소한 추상 클래스이다.
// 이후 우리는 이 BaseManager 클래스를 상속 받아
// i) Vector 대신 Map을 활용하여 Person 객체들을 관리하는 PMbyMap 클래스를 구현한다.
// ii) 또한 우리가 구현한 VectorPerson을 활용하여 Person 객체들을 관리하는
//     MyVectorTest 클래스를 구현할 예정이다.(과제 7-3에서)
//-----------------------------------------------------------------------------
abstract class BaseManager extends PersonGenerator
{
//    protected int ADD_SIZE = 10;
//    protected int REPLACE_SIZE = 5;
//    private Random rand;
//    protected int count;

//    public BaseManager() {
//        rand = new Random(0);
//        count = 10;
//    }
    public void run() {
        String menuStr =
                "======= Base Person Management Menu ========\n" +
                        "= 0.exit 1.display 2.clear 3.add 4.replace =\n" +
                        "= 5.remove 6.find 7.collections            =\n" +
                        "============================================\n";
        final int MENU_COUNT = 9; // 상수 정의
        while (true) {
            int menuItem = UI.selectMenu(menuStr, MENU_COUNT);
            switch(menuItem) {
                case 1: display();       break;
                case 2: clear();         break;
                case 3: add();           break;
                case 4: replace();       break;
                case 5: remove();        break;
                case 6: find();          break;
                case 7: collections();   break;
                case 0:                  return;
            }
        }
    }
    abstract public void display(); // Menu item 1

    public void clear() {  // Menu item 2
        clearAllElements(); // 아래 추상함수 설명 참고
        display();
    }
    public void add() { // Menu item 3
        addElements();
        display();
    }
    abstract public void replace(); // Menu item 4
    abstract public void remove(); // Menu item 5
    abstract public void find(); // Menu item 6
    abstract public void collections(); // Menu item 7
    //-------------------------------------------------------------------------------------------
    // 기타 abstract functions
    //-------------------------------------------------------------------------------------------
    abstract public void clearAllElements(); // 컬렉션의 모든 원소를 삭제함
    // Person 객체들을 자동으로 생성하여 컬렉션에 추가함; 이때 아래 getXXX() 함수를 활용한다.
    abstract public void addElements();
    //-------------------------------------------------------------------------------------------
    // Person 객체를 자동으로 생성할 때 아래 함수들을 사용하라.
    //-------------------------------------------------------------------------------------------
//    protected int getNewId() { return 1000+rand.nextInt(1000); }
//    protected String getNewName() { return families[rand.nextInt(families.length)] + (count++); }
//    protected double getNewWeight() {
//        double weight = 40 + rand.nextDouble() * 60; // 40 ~ 100 사이의 몸무게 생성
//        return (int)(weight * 10) / (double)10; // 소수점 한자리까지만 사용함
//    }
//    protected boolean getNewMarried(int id) { return (id % 2) == 1; }
//    protected String getNewAddress() {
//        return cities[rand.nextInt(cities.length)] + " " +
//                gus[rand.nextInt(gus.length)];
//    }
//    private String families[] =
//            { "Kimm", "Leem", "Park", "Choi", "Jeon", "Shim", "Shin", "Kang", "Yang", "Yoon",
//                    "Baek", "Ryoo", "Seoh", "Johh", "Baeh", "Moon", "Nahh", "Jooh", "Song", "Hong" };
//    private String cities[] =
//            { "Seoul", "Busan", "Gwangju", "Daejeon", "Incheon", "Daegu", "Ulsan" };
//    private String gus[] =
//            { "Jung-gu", "Nam-gu", "Buk-gu", "Dong-gu", "Seo-gu", };

}   // ch7_2: BaseManager class

class PersonGenerator
{
    static final int ADD_SIZE = 10;
    static final int REPLACE_SIZE = 5;

    private Random rand;
    int count, id, stYear;
    String name, address, department, company, position;
    double weight, GPA;
    boolean married;

    public PersonGenerator() {
        rand = new Random(0);
        count = 10;
    }
    public Person getNewPerson() {
        name = families[rand.nextInt(families.length)] + (count++);
        id = 1000+rand.nextInt(1000);
        weight = 40 + rand.nextDouble() * 60;
        weight = (int)(weight * 10) / (double)10;
        married = (id % 2) == 1;
        address = cities[rand.nextInt(cities.length)] + " " + gus[rand.nextInt(gus.length)];
        stYear = rand.nextInt(4)+1;
        GPA = rand.nextDouble() * 4.5;
        GPA = (int)(GPA * 10) / (double)10;
        department = departs[rand.nextInt(departs.length)];
        company = companies[rand.nextInt(companies.length)];
        position = positions[rand.nextInt(positions.length)];

        switch (rand.nextInt(3)) {
            case 0: return new Person(name, id, weight, married, address);
            case 1: return new Student(name, id, weight, married, address, department, GPA, stYear);
            case 2: return new Worker(name, id, weight, married, address, company, position);
            default: return null;
        }
    }
    private String families[] =
            { "Kimm", "Leem", "Park", "Choi", "Jeon", "Shim", "Shin", "Kang", "Yang", "Yoon",
                    "Baek", "Ryoo", "Seoh", "Johh", "Baeh", "Moon", "Nahh", "Jooh", "Song", "Hong" };
    private String cities[] =
            { "Seoul", "Busan", "Gwangju", "Daejeon", "Incheon", "Daegu", "Ulsan" };
    private String gus[] =
            { "Jung-gu", "Nam-gu", "Buk-gu", "Dong-gu", "Seo-gu", };
    private String departs[] =
            { "Physics", "Electronics", "Computer", "Chemistry", "Biology", "Micanical" };
    private String companies[] =
            { "Samsung", "Hyundai", "Kia", "SK", "LG", "Naver", "Kakao", "KT" };
    private String positions[] =
            { "Director", "Manager", "CEO", "CTO", "Chief", "CFO" };
}   // ch8_2: PersonGenerator class`

class PMbyMap extends BaseManager // ch7_2
{
    // Map은 HashMap과 TreeMap의 수퍼 클래스(인터페이스)이며 map = hashMap으로 업캐스팅 가능함
    private Map< String, Person > map;
    private HashMap< String, Person > hashMap = null;
    private TreeMap< String, Person > treeMap = null;

    public PMbyMap() {
        String menuStr =
                "====== Map Menu =======\n" +
                        "= 0.HashMap 1.TreeMap =\n" +
                        "=======================\n";
        int mapType = UI.selectMenu(menuStr, 2);
        if (mapType == 0)
            map = hashMap = new HashMap< String, Person >();
        else if (mapType == 1)
            map = treeMap = new TreeMap< String, Person >();

        // 위에서 선택된 Map의 종류에 상관없이 이 이후부터는 map을 교재의 예제에서 보여준
        // HashMap이라 생각하고 멤버 함수들을 사용하여 코딩하면 된다.
        add(); // BaseManager 멤버 함수
    }
    static public void display(Map< String, Person > map) {
        System.out.println("Map Size: "+map.size());
        System.out.println("---------------------------------------------");
        // 교재에서 소개된 이 방식은 간단하기는 하나 각 키에 대해 매번 다시 map.get(name)을 통해
        // map 검색을 다시 해야 하는 오버헤드가 있다.
        /*
        Set< String > keySet = map.keySet();
        for (var name: keySet)
            System.out.println(map.get(name));
        */
        // 아래 방식은 EntrySet을 구하는데 map의 모든 entry를 다 가지고 있는 집합이다.
        // 각 entry는 { 키, 값 }으로 구성된다.
        // 각 entry e에 저장된 키와 값은 e.getKey(), e.getValue()를 통해 얻을 수 있다.
        // 이 방식을 사용하면 매번 map을 다시 검색하지 않아도 된다.
        Set< Map.Entry< String, Person > > entrySet = map.entrySet();
        for (var e: entrySet)
            System.out.println(e.getValue());
    }
    public void display() { // Menu item 1
        display(map);
    }
    // 모든 키 값에 대해 새로운 객체를 생성한 후 이름은 기존과 동일하게 설정한다.
    // 그런 후 동일한 키에 대해 값(객체)만 교체한다.
    public void replace() { // Menu item 4
        int i = 0;
        Set< String > keySet = map.keySet();
        for (var name: keySet) {
            var p = getNewPerson();
            p.set(name);
            map.put(name, p);
            if (++i == REPLACE_SIZE) break; // 초반의 REPLACE_SIZE 개수만큼만 교체함
        }
        display();
    }
    public void remove() { // Menu item 5
        if(map.isEmpty()) {
            System.out.println("no entry to remove");
            return;
        }
        String name = UI.getNext("name to delete? ");
        map.remove(name);
        display();
    }
    public void find() { // Menu item 6
        String name = UI.getNext("name to find? ");
        Person p = map.get(name);
        if(p != null){
            System.out.println(p);
        }else{
            System.out.println( name + ": NOT found");
        }
    }
    public void collections() { // Menu item 7
        if (hashMap == null)
            new CollectionsByTreeMap(treeMap).run();
        else
            new CollectionsByHashMap(hashMap).run();
    }
    public void clearAllElements() {  // BaseManager::clear()에서 호출됨
        map.clear();
    }

    public void addElements() {  // BaseManager::add()에서 호출됨
        for (int i = 0; i < ADD_SIZE; ++i) {
            var p = getNewPerson();
            map.put(p.getName(), p);
        }
    }
}   // ch7_2: PMbyMap class

abstract class CollectionsMenu // ch7_1
{
    public void run() {
        String menuStr =
                "======================= Collections Menu =======================\n" +
                        "= 0.exit 1.display 2.min 3.max 4.sort 5.reverse 6.binarySearch =\n" +
                        "================================================================\n";
        final int MENU_COUNT = 7;
        while (true) {
            int menuItem = UI.selectMenu(menuStr, MENU_COUNT);
            switch(menuItem) {
                case 1:display();         break;
                case 2:min();             break;
                case 3:max();             break;
                case 4:sort();            break;
                case 5:reverse();         break;
                case 6:binarySearch();    break;
                case 0:                   return;
            }
        }
    }
    abstract public void display(); // Menu item 1
    abstract public void min(); // Menu item 2
    abstract public void max(); // Menu item 3
    abstract public void sort(); // Menu item 4
    abstract public void reverse(); // Menu item 5
    abstract public void binarySearch(); // Menu item 6
}
class CollectionsByList extends CollectionsMenu // ch7_1
{
    // List는 인터페이스로 ArrayList, Vector, LinkedList의 수퍼 클래스이다.
    private List< Person > list; // list는 벡터라 생각하고 사용하면 된다.

    public CollectionsByList(List< Person > list) {
        this.list = list; // list는 PersonManager의 pVector가 업캐스팅된 것이다.
    }
    @Override
    public void display() { // Menu item 1
        PersonManager.display(list);
    }
    @Override
    public void min() { // Menu item 2
        if (list.isEmpty()) {
            return;
        }

        // Finding the person with the lexicographically smallest name.
        Person pe = Collections.min(list);

        // Printing the person found.
        System.out.println(pe);

    }
    @Override
    public void max() { // Menu item 3
        if (list.isEmpty()) {
            return;
        }

        Person pe = Collections.max(list);
        System.out.println(pe);
    }
    @Override
    public void sort() { // Menu item 4
        Collections.sort(list);
        display();
    }
    @Override
    public void reverse() { // Menu item 5
        Collections.reverse(list);
        display();
    }
    @Override
    public void binarySearch() {  // Menu item 6
        String name = UI.getNext("For binary search, it's needed to sort in advance. Name to search? ");
        Person p = new Person(name);
        int i = Collections.binarySearch(list, p);
        if(i >= 0){
            System.out.println(list.get(i));
        }else{
            System.out.println(name + " is NOT found.");
        }
        // 주의: 이진 검색하기 전에 먼저 list가 정렬이 되어 있어야 한다.
    }
}   // ch7_1: CollectionsByList class

abstract class CollectionsByMap extends CollectionsMenu // ch7_2
{
    public void display(Map< String, Person > map) {
        PMbyMap.display(map);
        // ToDo: PMbyMap의 display(map)을 호출하라.
    }
    public void searchMap(Map< String, Person > map) {
        String name = UI.getNext("Name to search? ");
        Person p = map.get(name);
        if(p != null){
            System.out.println(p);
        }else{
            System.out.println( name + ": NOT found");
        }
    }
}   // ch7_2: CollectionsByMap class

class CollectionsByTreeMap extends CollectionsByMap // ch7_2
{
    private TreeMap< String, Person > map;

    public CollectionsByTreeMap(TreeMap< String, Person > map) { this.map = map; }

    public void display() { display(map); }
    public void min() {
        // 첫번째 entry가 키가 가장 작은 엔트리이다. 이름 순서상 가장 앞쪽 이름임
        Map.Entry< String, Person > e = map.firstEntry();
        if (e != null) System.out.println(e.getValue());
    }
    public void max() {
        // 마지막 entry가 키가 가장 큰 엔트리이다. 이름 순서상 가장 뒤쪽 이름임
        Map.Entry< String, Person > e = map.lastEntry();
        if (e != null) System.out.println(e.getValue());
    }
    public void sort() { display(); } // 키가 이미 정렬되어 있으므로 바로 보여 줌

    // descendingMap()을 통해 키의 역순으로 된 map를 구할 수 있음
    public void reverse() { display(map.descendingMap()); }

    public void binarySearch() { searchMap(map); } // 맵에서 바로 검색함

}   // ch7_2: CollectionsByTreeMap class

class CollectionsByHashMap extends CollectionsByMap // ch7_2
{
    private HashMap< String, Person > map;

    public CollectionsByHashMap(HashMap< String, Person > map) { this.map = map; }

    public void display() {  display(map); }
    public void min() {
        if(map.isEmpty())
            return;
        // Finding the person with the lexicographically smallest name.
        String s = Collections.min(map.keySet());

        Person p = map.get(s);
        System.out.println(p);

    }
    public void max() {
        if(map.isEmpty())
            return;
        // Finding the person with the lexicographically smallest name.
        String s = Collections.max(map.keySet());

        Person p = map.get(s);
        System.out.println(p);
    }
    public void sort() {
        // map의 keySet을 이용하여 벡터를 생성함
        var keyList = new Vector< String >(map.keySet());
        Collections.sort(keyList);

        for (var name:keyList)
            System.out.println(map.get(name));

    }
    public void reverse() {
        var keyList = new Vector< String >(map.keySet());
        Collections.reverse(keyList);

        for (var name:keyList)
            System.out.println(map.get(name));

    }
    public void binarySearch() { searchMap(map); }  // 맵에서 바로 검색함

}   // ch7_2: CollectionsByHashMap class

class MyVector<E>
{
    static final int DEFAULT_SIZE = 10;

    private Object[] persons; // Person 객체 참조들의 배열, 즉 배열에 저장된 값이 Person 객체의 주소이다.
    private int count;        // persons 배열에 현재 삽입된 객체의 개수

    public MyVector() { this(DEFAULT_SIZE); }

    public MyVector(int capacity) {
        count = 0; // persons 배열에 현재 삽입된 객체의 개수는 0
        //System.out.println("VectorPerson::VectorPerson("+capacity+")");
        persons = new Object[capacity]; // 객체 참조 배열 할당
    }
    // persons[index]의 값을 반환
    @SuppressWarnings("unchecked")
    public E get(int index) { return (E)persons[index]; }

    // persons[index]의 값을 p로 새로 교체하고 과거의 persons[index] 값을 반환
    @SuppressWarnings("unchecked")
    public E set(int index, Object p) {
        Object per = persons[index];
        persons[index] = p;
        return (E)per;
    }

    // 할당 받은 persons 배열의 전체 길이를 반환함 (count가 아님)
    public int capacity() { return persons.length; }

    // persons 배열에 현재 삽입된 객체의 개수를 0으로 설정
    public void clear() { count=0; }

    // 현재 삽입된 객체 참조가 하나도 없으면 true, 있으면 false를 반환한다.
    // if 문장을 사용하지 말고 한 문장(return 비교연산자)으로 완셩할 것
    public boolean isEmpty() { return count==0; }

    // 현재 삽입된 객체의 개수를 반환
    public int size() { return count; }

    // index 위치의 객체 p를 삭제한다. 즉, index+1부터 끝까지 객체들을 한칸씩 왼쪽으로 밀어 주어야 한다.
    // 자바에는 객체를 삭제하는 delete 명령어가 없다. 따라서 객체를 별도로 삭제할 필요는 없고 무시하라.
    void remove(int index) {
        for(int i=index; i<count-1; i++){
            //System.out.println(count + " " + i);
            persons[i] = persons[i+1];
        }
        --count;
    }
    // persons 배열에 마지막 삽입된 원소 뒤에 새로운 원소 p를 삽입하고 현재 삽입된 객체 개수 증가
    // persons[]의 배열 크기가 작으면 extend_capacity()를 호출하여 먼저 배열을 확장한다.
    public void add(E p) {
        if (count >= persons.length)
            extend_capacity();
        persons[count++] = p;
    }
    // 먼저 index부터 끝까지 객체들을 한칸씩 뒤로 밀어 준 후 index 위치에 객체 p를 삽입한다.
    // persons[]의 배열 크기가 작으면 extend_capacity()를 호출하여 먼저 배열을 확장한다.
    public void add(int index, E p) {
        if( count >= persons.length)
            extend_capacity();

        //어떻게 i = 5 , index = 0 이면 계산을 5 4 3 2 1 5번을 계산하는데 어떻게 6개의 배열이 아닌 5개 밖에 안 나오는가
        for(int i=++count; i>index; i--){
            persons[i] = persons[i-1];
        }

        persons[index] = p;

    }
    // persons[]의 배열 크기를 두배로 확장한다.
    // 기존 persons 변수를 다른 배열 변수에 임시로 저장한 후
    // 현재의 두배 크기의 배열을 새로 할당 받아 persons에 저장한다.
    // 임시 변수에 있던 기존 값들을 모두 persons[]에 복사한다.
    public void extend_capacity() {
        Object[] tmp = new Object[persons.length * 2];

        for(int i=0; i<persons.length; i++){
            tmp[i] = persons[i];
        }
        persons = tmp;
        //System.out.println("VectorPerson: capacity extended to " + persons.length);
    }
}   // VectorPerson class: ch4_2

class MyVectorTest extends BaseManager // ch7_3
{
    private MyVector< String >  nameVct;    // Person::name 멤버들을 저장하는 벡터
    private MyVector< Integer > idVct;      // Person::id 멤버들을 저장하는 벡터
    private MyVector< Double >  weightVct;  // Person::weight 멤버들을 저장하는 벡터
    private MyVector< Boolean > marriedVct; // Person::married 멤버들을 저장하는 벡터
    private MyVector< String >  addressVct; // Person::address 멤버들을 저장하는 벡터

    public MyVectorTest() {
        // ToDo: MyVector< >의 기본 생성자를 이용하여 5개의 멤버 벡터들을 생성하라.
        nameVct = new MyVector<>();
        idVct = new MyVector<>();
        weightVct = new MyVector<>();
        marriedVct = new MyVector<>();
        addressVct = new MyVector<>();

        // 아래 add()는 각 벡터에 ADD_SIZE개의 원소를 자동 삽입함
        add();  // BaseManager의 멤버 함수임; 이 함수는 다시 아래의 addElements()를 호출함
    }
    public void displayPerson(int i) { // 한 사람의 정보를 출력함
        System.out.println("[" + i + "] " + nameVct.get(i)+" "+idVct.get(i)+
                " "+weightVct.get(i)+" "+marriedVct.get(i)+" :"+addressVct.get(i)+":");
    }
    public void display() { // Menu item 1: 모든 사람들의 정보를 출력함
        for (int i = 0, count = idVct.size(); i < count; ++i)
            displayPerson(i);
    }
    public void replace() { // Menu item 4
        // 각 벡터의 총 원소 중 초반의 REPLACE_SIZE개의 사람 정보를 모두 수정함
        int count = Math.min(REPLACE_SIZE, idVct.size());
        for (int i = 0; i < count; ++i) {
            Person p = getNewPerson(); // 새로운 객체를 자동으로 생성함
            nameVct.set(i, p.getName());
            idVct.set(i, p.getId());
            weightVct.set(i, p.getWeight());
            marriedVct.set(i, p.getMarried());
            addressVct.set(i, p.getAddress());
        }
        display();
    }
    public void remove() { // Menu item 5
        if (idVct.size() == 0) {
            System.out.println("no entry to remove");
            return;
        }
        int index = UI.getIndex("index to delete? ", idVct.size());
        nameVct.remove(index);
        idVct.remove(index);
        weightVct.remove(index);
        marriedVct.remove(index);
        addressVct.remove(index);
        // ToDo: 5개의 각각의 멤버 벡터에 대해 index 항목을 삭제하라.
        display();
    }
    public void find() { // Menu item 6
        String name = UI.getNext("name to find? ");
        for(int i = 0; i< nameVct.size(); i++){
            //System.out.println(nameVct.get(i));
            if(nameVct.get(i).equals(name)){
                displayPerson(i);
                return;
            }
        }
        System.out.println(name + ": NOT found");

        // ToDo: for를 이용하여 nameVct에서 입력 받은 name 같은 이름을 찾아서
        //       찾은 경우 그 사람의 정보를 출력하라.
        //       찾지 못한 경우 아래를 출력하라.
    }
    public void collections() { // Menu item 7
        System.out.println("not supported by MyVector");
    }
    public void clearAllElements() {  // BaseManager::clear()에 의해 호출됨
        nameVct.clear();
        idVct.clear();
        weightVct.clear();
        marriedVct.clear();
        addressVct.clear();
        // ToDo: 5개의 각각의 멤버 벡터에 대해 모든 원소들을 삭제하라.
    }
    public void addElements() {   // BaseManager::add()에 의해 호출됨
        // ADD_SIZE개의 Person 정보를 자동 생성하여 별개의 벡터에 보관함
        for (int i = 0; i < ADD_SIZE; ++i) {
            Person p = getNewPerson();
            // 각각의 벡터의 끝에 자동 생성된 Person 멤버들을 추가한다.
            int id = p.getId();
            nameVct.add(p.getName());
            idVct.add(p.getId());
            weightVct.add(p.getWeight());
            marriedVct.add(p.getMarried());
            addressVct.add(p.getAddress());
        }
    }
}   // ch7_3: MyVectorTest class

class Inheritance
{
    Student s;
    Worker  w;

    public Inheritance() {
        s = new Student("s1", 1, 65.4, true,  "Jongno-gu Seoul",  "Physics", 3.8, 1);
        w = new Worker ("w1", 3, 33.3, false, "Kangnam-gu Seoul", "Samsung", "Director");
    }
    public void run() {
        String menuStr =
                "***** Inheritance Menu ******\n" +
                        "* 0.exit 1.Student 2.Worker *\n" +
                        "*****************************\n";
        final int MENU_COUNT = 3; // 상수 정의
        while (true) {
            int menuItem = UI.selectMenu(menuStr, MENU_COUNT);
            switch(menuItem) {
                case 1: student(); break;
                case 2: worker();  break;
                case 0:            return;
            }
        }
    }
    void compare(Person p1, Person p2) {
        System.out.println("p1: " + p1);
        System.out.println("p2: " + p2);
        System.out.println("p1.equals(p2) : " + p1.equals(p2));
        System.out.println("--------------------");
    }
    Person whatAreYouDoing(Person p) {
        p.whatAreYouDoing();
        return p;
    }

    void input(Person p, String msg) {
        System.out.print("input "+msg+": ");
        p.input(UI.scan);
        if (UI.echo_input) System.out.println(p); // 자동체크에서 사용됨
    }

    Person clone(Person p) {
        Person c = p.clone();
        return c;
    }

    void assign(Person d, Person s) {
        d.assign(s); // s를 d에 복사
    }

    Person newInput(Boolean isStudent, String msg) {
        Person p = null;
        System.out.print("input new "+msg+": ");
        if (isStudent)
            p = new Student(UI.scan);
        else
            p = new Worker(UI.scan);
        if (UI.echo_input) System.out.println(p); // 자동체크에서 사용됨
        return p;
    }

    void student() {
        var s1 = new Student(s);
        var s2 = new Student(s1);

        System.out.println("--------------------");
        s2.set("s2");
        compare(s1, s2); // 업캐스팅

        s2.set(s1.getName());
        s2.setGPA(s2.getGPA()-1.0);
        compare(s1, s2);

        s2.setDepartment(s1.getDepartment()+"-Electronics");
        compare(s1, s2);

        s2.setDepartment(s1.getDepartment());
        s2.setYear(s1.getYear()+1);
        compare(s1, s2);

        s2.setYear(s1.getYear());
        compare(s1, s2);

        s2.set("s2");
        Student s3 = (Student)whatAreYouDoing(s2); // 함수호출:다운캐스팅 & 리턴:업캐스팅
        System.out.println();
        s3.whatAreYouDoing();

        s3 = (Student)clone(s2);
        System.out.println("s3: " + s3);
        System.out.println("--------------------");

        System.out.println("s2: " + s2);
        s1 = new Student("", 0, 0.0, false, "", "", 0.0, 0);
        assign(s2, s1); // (destination, source): destination = source
        System.out.println("s2: " + s2);
        System.out.println("--------------------");

        input(s2, "student"); // s2 1 56.9 false :Gangnam-gu Seoul: Physics 2.0 1
        System.out.println("s2: " + s2);
        System.out.println("--------------------");

        Student s4 = (Student)newInput(true, "student");
        // s4 1 56.9 false :Gangnam-gu Seoul: Physics 2.0 1
        System.out.println("s4: " + s4);
    }
    void worker() {
        var w1 = new Worker(w);
        var w2 = new Worker(w1);

        System.out.println("--------------------");
        w2.set("w2");
        compare(w1, w2); // 업캐스팅

        w2.set(w1.getName());
        w2.setCompany(w1.getCompany()+"-Hyundai");
        w2.setPosition(w1.getPosition());
        compare(w1, w2);
        w2.setCompany(w1.getCompany());
        w2.setPosition(w1.getPosition()+"-Manager");
        compare(w1, w2);
        w2.setPosition(w1.getPosition());
        compare(w1, w2);

        w2.set("w2");
        Worker w3 = (Worker)whatAreYouDoing(w2);  // 다운캐스팅
        System.out.println();
        w3.whatAreYouDoing();

        w3 = (Worker)clone(w2);
        System.out.println("w3: " + w3);
        System.out.println("--------------------");

        System.out.println("w2: " + w2);
        w1 = new Worker("", 0, 0.0, false, "", "", "");
        assign(w2, w1); // (destination, source): destination = source
        System.out.println("w2: " + w2);
        System.out.println("--------------------");

        input(w2, "worker"); // w2 3 44.4 true :Jongno-gu Seoul: Samsung Director
        System.out.println("w2: " + w2);
        System.out.println("--------------------");

        Worker w4 = (Worker)newInput(false, "worker");
        // w4 3 44.4 true :Jongno-gu Seoul: Samsung Director
        System.out.println("w4: " + w4);
    }
}   // Inheritance class: ch5_1

class FileManager extends PersonGenerator // ch8_1
{
    static final String HOME_DIR = "data"; // 상수 정의: 파일들을 생성할 폴더 이름
    static final String TEXT_PATH_NAME = HOME_DIR+"/persons.txt"; // 8_3
    private List list;

    FileManager(List list) {
        var dir = new File(HOME_DIR);
        if (!dir.exists()) dir.mkdir(); // 프로젝트 폴더에 "data" 폴더가 없을 경우 새로 생성
        this.list = list;
    }
    public void run() {
        String menuStr =
                "====================== File Management Menu =====================\n" +
                        "= 0.exit 1.fileList 2.delete 3.rename 4.addFiles 5.addDir       =\n" +
                        "= 6.deleteAll 7.show 8.copy 9.append 10.display 11.clear 12.add =\n" +
                        "= 13.saveText   14.loadText   15.saveTextAs   16.loadTextFrom   =\n" +
                        "=================================================================\n";
        final int MENU_COUNT = 17; // 상수 정의
        while (true) {
            int menuItem = UI.selectMenu(menuStr, MENU_COUNT);
            try {  // ch8_2
                switch(menuItem) {
                    case 1: fileList();      break;
                    case 2: delete();        break;
                    case 3: rename();        break;
                    case 4: addFiles();      break;
                    case 5: addDir();        break;
                    case 6: deleteAll();     break;
                    case 7: show();          break;  // ch8_2
                    case 8: copy();          break;  // ch8_2
                    case 9: append();        break;  // ch8_2
                    case 10: display();      break;  // ch8_2
                    case 11: clear();        break;  // ch8_2
                    case 12: add();          break;  // ch8_2
                    case 13: saveText();     break;  // ch8_3
                    case 14: loadText();     break;  // ch8_3
                    case 15: saveTextAs();   break;  // ch8_3
                    case 16: loadTextFrom(); break;  // ch8_3
                    case 0:                  return;
                }
            } catch (IOException e) { System.out.println("file I/O error: "+e); }
        }
    }
    void printFileInfo(File f) {
        long t = f.lastModified();
        t = 1700000000000L;  // 2023-11-15 오전 07:13; 자동 체크 때 사용할 예정임
        System.out.printf("%-20s %c %tF %tH:%tM %9d\n",
                f.getName(), f.isDirectory()? 'D':'F', t, t, t, f.length());
    }
    File[] fileList() { // menu item 1
        var dir = new File(HOME_DIR);
        File files[] = dir.listFiles();
        if (files == null) return null; // HOME_DIR이 존재하지 않을 경우 null
        for (int i=0; i < files.length; i++) {
            File f = files[i];
            System.out.printf("[%d] ", i);
            printFileInfo(f);
        }
        System.out.println("-----------------");
        System.out.println("["+HOME_DIR+"] directory: "+files.length+" files");
        return files;
    }
    File selectFile(String preMsg, String postMsg, boolean onlyFile) {
        File files[] = fileList();
        if (preMsg.length() != 0) preMsg += " ";
        while (true) {
            String msg = "index number of a "+preMsg+"file to "+postMsg+" [-1:stop]? ";
            int val = UI.getInt(msg);
            if(val >= 0 && val < files.length) {
                if(files[val].isDirectory() && onlyFile) {
                    System.out.println("can't select directory: " + files[val].getName());
                    continue;
                }return files[val];
            }else if(val == -1){
                return null;
            }
            else{
                System.out.println("invalid index number: " + val);
            }
        	/* ToDo
        	UI의 적절한 함수를 사용하여 msg를 출력하고 정수 값(val)을 입력 받는다.
        	val이 정상적인 인덱스 값이면 해당 File 객체를 리턴한다.
        	사용자가 파일 선택을 취소한 경우 즉 val이 -1이면 null을 리턴한다. 그렇지 않으면
        	에러 메시지("invalid index number: ")와 val을 출력한다.(다시 입력 받아야 함)
        	*/
        }
    }
    void delete() { // menu item 2
        File f = selectFile("", "delete",false);
        if (f==null) return;
        f.delete();
        fileList();
	    /* ToDo
	    selectFile() 함수를 호출하여 삭제할 파일의 File 객체 f를 구한다.
	        selectFile() 함수 호출 시 필요한 경우 인자를 ""로 지정해 주어도 된다.
	    사용자가 파일 선택을 취소했을 경우 바로 리턴한다.
	    File 객체 f를 이용하여 해당 파일을 삭제한다.
	    파일 목록을 보여 준다.
	    */
    }

    void rename() { // menu item 3
        File src = selectFile("source", "rename",false);
        if (src == null) return; // 사용자가 파일 선택을 취소했을 경우
        String name = UI.getNext("target file name? ");
        File dst = new File(src.getParent() + "/" + name);
        if(dst.exists()) {
            System.out.println(dst.getName() + " already exists");
            return;
        }
        src.renameTo(dst);
        fileList();
	    /* ToDo
	    UI의 적절한 함수를 사용하여 "target file name? "을 출력하고
	    변경할 새 파일 이름(한 단어로)을 입력 받는다.
	    변경할 이름의 File 객체 dst를 생성한다.
	    (이때 디렉터리는 src의 부모 디렉터리(src.getParent())를 얻어와 동일하게 설정해 주어야 한다.
	     addFiles()의 새로 생성할 파일의 File 객체 생성 참조)
	    파일이 이미 존재하면 파일이름과 함께 " already exists" 출력하고 리턴한다.
	    src의 이름을 dst의 이름으로 변경한다.
	    파일 목록을 보여 준다.
	    */
    }
    void addFiles() { // menu item 4: 4개의 text 파일을 자동 생성함
        for (int i = 0, count = 0; count < 4; ++i, ++count) {
            var f = new File(HOME_DIR + "/t" + i + ".txt"); // 파일 이름이 t0부터 시작함
            if (f.exists()) { --count; continue; }          // 동일한 이름이 있을 경우 건너 뜀
            try {
                var fout = new PrintStream(f);
                for (int j = 0; j <= i; ++j)
                    fout.println("This is a text file to test File Management Menu.");
                fout.close();
            } catch (IOException e) { System.out.println(e); }
        }
        fileList();
    }
    void addDir() { // menu item 5: 2개의 디렉터리를 자동으로 생성함
        /* ToDo: 먼저 addFiles() 함수의 내용을 복사해서 삽입하라.
        count를 수정하고 아래 실행 결과를 참고하여 생성할 디렉토리 이름을 적절하게 지정하라.
        try catch 문장 전체를 삭제하고 대신 그 자리에 디렉토리를 생성하라.
        */
        for (int i = 0, count = 0; count < 2; ++i, ++count) {
            var f = new File(HOME_DIR + "/d" + i); // 파일 이름이 t0부터 시작함
            if (f.exists()) { --count; continue; }          // 동일한 이름이 있을 경우 건너 뜀
            f.mkdir();
        }
        fileList();
    }

    void deleteAll() { // menu item 6: 모든 파일 및 빈 디렉터리들을 삭제함
        var dir = new File(HOME_DIR);
        File files[] = dir.listFiles();
        for (int i=0; i < files.length; i++) {
            File f = files[i];
            f.delete();
        }
        files = dir.listFiles();
        System.out.println("-----------------");
        System.out.println("["+HOME_DIR+"] directory: "+files.length+" files");
        /* ToDo: 먼저 fileList() 함수의 내용을 복사해서 삽입하라.
        for 문 내에서
            files[i]의 파일을 삭제한다.
            나머지 문장들은 모두 삭제한다.
	    파일 목록을 보여 준다.
        */
    }
    void copyFile(InputStream in, OutputStream out) throws IOException {
        byte [] buf = new byte[1024 * 8];
        while(true){
            int n = in.read(buf);
            out.write(buf, 0, n);
            if(n < buf.length)
                break;
        }

    }
    void show() throws IOException { // menu item 7: show file content: 8_2
        FileInputStream f1 = new FileInputStream(selectFile("", "display",true));
        System.out.println("-----------------");
        copyFile(f1, System.out);
        System.out.println("-----------------");
        f1.close();
        /* ToDo
        selectFile()을 호출하여 console에 출력할 File 객체 src를 구한다.
        사용자가 파일 선택을 취소한 경우 리턴한다.
        src 파일에 대한 FileInputStream 객체 fi를 생성한다.
        copyFile(fi, System.out)을 호출한다. 즉, fi 파일을 console(System.out)에 복사한다.
        fi를 닫는다.
        */
        // 위와 같이 copyFile(fi, System.out)에서 out.write()를 이용하여 파일 내용을
        // console에 출력한다. 즉, System.out에 print() println() 뿐만 아니라
        // write(buf)를 이용해서도 text 파일 내용을 출력할 수 있다는 것을 의미한다.
    }
    void copy() throws IOException { // menu item 8: file copy: 8_2
        File src = selectFile("source", "copy",true);
        if (src == null) {
            return; // 사용자가 선택을 취소하면 함수를 종료
        }
        String target = UI.getNext("target file name? ");
        File dst = new File(src.getParent() + "/" + target);
        FileInputStream fi = new FileInputStream(src);
        FileOutputStream fo = new FileOutputStream(dst);
        copyFile(fi ,fo);
        fi.close();
        fo.close();
        fileList();
        /* ToDo
        selectFile()을 호출하여 복사할 원본 File 객체 src를 구한다.
        사용자가 파일 선택을 취소한 경우 리턴한다.
        UI의 함수를 이용하여 "target file name? " 을 출력하고 target 파일이름(한 단어)을 입력 받는다.
        입력 받은 이름 target을 이용하여 File 객체 dst을 생성한다.
        (이때 디렉터리는 src의 부모 디렉터리(src.getParent())를 얻어와
         동일하게 설정해 주어야 한다. rename() 함수 참조)
        src 파일에 대한 FileInputStream 객체 fi를 생성한다.
        dst 파일에 대한 FileOutputStream 객체 fo를 생성한다.
        copyFile()을 호출하여 파일 내용을 복사한다.
        열러진 파일들을 닫는다.
        파일 목록을 보여 준다.
        */
    }

    void append() throws IOException { // menu item 8: file copy: 8_2
        File src = selectFile("source", "append",true);
        if (src == null) {
            return; // 사용자가 선택을 취소하면 함수를 종료
        }
        File dst = selectFile("target", "append",true);
        if (dst == null) {
            return; // 사용자가 선택을 취소하면 함수를 종료
        }

        FileInputStream fi = new FileInputStream(src);
        FileOutputStream fo = new FileOutputStream(dst, true);
        copyFile(fi ,fo);
        fi.close();
        fo.close();
        fileList();
        /* ToDo: 위 copy() 내용을 복사해 온 후 아래 내용을 참고하여 수정하라.
        selectFile()을 호출하여 원본 File 객체 src를 구한다.
        사용자가 파일 선택을 취소한 경우 리턴한다.
        selectFile()을 호출하여 원본 파일을 추가할 타켓 File 객체 dst를 구한다.
        사용자가 파일 선택을 취소한 경우 리턴한다.
        src 파일에 대한 FileInputStream 객체 fi를 생성한다.
        dst 파일에 대한 FileOutputStream 객체 fo를 생성하되,
            항상 파일 끝에 데이타를 추가할 수 있게 옵션을 설정해 준다.
        copyFile()을 호출하여 파일 내용을 복사한다. 그러면 자동으로 dst의 뒤에 src 내용이 추가된다.
        열러진 파일들을 닫는다.
        파일 목록을 보여 준다.
        */
    }
    void display() {  // menu item 10: 8_2
        PersonManager.display(this.list);
        System.out.println("-----------------");
        /* ToDo: 실행 결과처럼 list의 원소의 개수와 " entries"를 출력한다. */
        System.out.println(list.size() + " entries");
    }
    void clear() {  // menu item 11: 8_2
        list.clear();
        display();
    }
    void add() { // menu item 12: 8_2
        for (int i = 0; i < ADD_SIZE; ++i) {
            list.add(getNewPerson());
        }
        display();
    }
    void saveTextFile(String pathName) throws IOException { // 8_3
        var fout = new PrintStream(pathName);
        for(var t : list){
            Person p = (Person)t;
            fout.println(p.getDelimChar()+" "+t);
        }
        fout.close();
        fileList();
    }
    void saveText() throws IOException { // menu item 13: 8_3
        saveTextFile(TEXT_PATH_NAME);
    }
    void loadTextFile(String pathName) throws IOException {  // 8_3
        var fin = new FileInputStream(pathName);
        Scanner sc = new Scanner(fin);
        list.clear();

        boolean saved_echo_input = UI.echo_input;
        UI.echo_input = false;  // 자동오류체크시 파일에서 입력될 경우 출력하지 않도록 함
        while (true) {
            try {
                Person p = Factory.inputPerson(sc);
                if(p != null)
                    list.add(p);
            }
            catch (NoSuchElementException e) { break; }
            // 스캐너로 파일을 끝까지 다 읽었는데 또 읽으면 위 예외가 발생함;
            // 중요: 스캐너를 통해 파일을 읽을 때는 이걸 통해 읽기를 종료해야 함
            catch (Exception e) { System.out.println("scanner error: "+e); }
        }
        UI.echo_input = saved_echo_input;
        sc.close();
        display();
    }

    void loadText() throws IOException { // menu item 14: 8_3
        loadTextFile(TEXT_PATH_NAME);
    }
    String getNewFilePath(String msg) {  // 8_3
        fileList();
        String filename = UI.getNext("new "+msg+" file name to save? ");
        File f = new File(HOME_DIR + "/" + filename);
        if (f.exists()) { System.out.println(f.getName() + ": already exists"); return null;}
        return f.getPath();
        /* ToDo
        파일의 목록을 보여 주어라.
        UI의 적절한 함수를 호출하여 "new "+msg+" file name to save? " 보여 주고
        새로운 파일 이름 fileName을 한 단어로 입력 받아라.
        HOME_DIR과 fileName을 이용하여 File 객체 f를 생성하라. (addFiles() 참고)
        파일이 이미 존재하는 파일이라면
            파일 이름과 ": already exists"를 출력한 후 null을 반환하라.
        객체 f의 path를 리턴하라.
        */
    }
    void saveTextAs() throws IOException { // menu item 15: 8_3
        String name = getNewFilePath("text");
        if(name == null) return;
        saveTextFile(name);
    }
    void loadTextFrom() throws IOException { // menu item 16: 8_3
        File f = selectFile("text", "load", true);
        if(f==null) return; loadTextFile(f.getPath());
    }

}  // ch8_1: FileManager class

//===============================================================================
// class Ch2
//===============================================================================
class Ch2 {
    public static void run() {
        final int MENU_COUNT = 6;
        String menuStr =
                "************* Ch2 Menu ***********\n" +
                        "* 0.exit 1.output 2.readToken    *\n" +
                        "* 3.readLine 4.operator 5.switch *\n" +
                        "**********************************\n";

        //System.out.print(menuStr);
        while (true) {
            int menuItem = UI.selectMenu(menuStr, MENU_COUNT);
            // TODO: 입력 받은 memnuItem 값을 프로그램 실행결과처럼 출력하라. 예) menu item: 1
            //System.out.print("menu item: " + menuItem);
            switch (menuItem) {
                case 0:
                    return;
                case 1:
                    output();
                    break;
                case 2:
                    readToken();
                    break;
                case 3:
                    readLine();
                    break;
                case 4:
                    operator();
                    break;
                case 5:
                    switchCase();
                    break;
            }
        }
    }

    public static void output() {
        //System.out.println("output");
        String toolName = "JDK";
        double version = 1.8;
        String released = "is released.";

        System.out.println(toolName + version + released);
        System.out.println(toolName + " " + version + " " + released);
        // TODO: 하나의 출력문으로 위 세 변수를 이용하여 "JDK1.8is released."가 출력되게 하라.
        // TODO: 하나의 출력문으로 위 세 변수를 이용하여 "JDK 1.8 is released."가 출력되게 하라.

        int i1 = 1, i2 = 2, i3 = 3; // 변수 선언과 함께 초기화

        System.out.println(i1 + i2 + i3);
        System.out.println("" + i1 + i2 + i3);
        System.out.println(i1 + i2 + i3 + " " + i1 + i2 + i3);
        // TODO: 하나의 출력문으로 위 세 변수를 이용하여 "6"가 출력되게 하라.
        // TODO: 하나의 출력문으로 위 세 변수를 이용하여 "123"가 출력되게 하라.
        // TODO: 하나의 출력문으로 위 세 변수를 이용하여 "6 123"가 출력되게 하라.

        boolean b = true;
        double d = 1.2;

        System.out.println(b + " " + !b + " " + d);
        // TODO: 하나의 출력문으로 위 두 변수를 이용하여 "true false 1.2"가 출력되게 하라.
        //       변수만 사용하고 "true", "false"를 직접 출력하지는 마라.
    }

    public static void readToken() {
        String name;    // 이름
        int id;      // Identifier
        double weight;  // 체중
        boolean married; // 결혼여부
        String address; // 주소

        System.out.println("person information(name id weight married :address:):");

        name = UI.scan.next();
        id = UI.scan.nextInt();
        weight = UI.scan.nextDouble();
        married = UI.scan.nextBoolean();

        // TODO: 아래 실행결과를 참고하여 "person ... ):" 문자열을 출력하라.
        // TODO: UI.scan을 이용하여 name, id, weight, married 값을 입력 받아라.

        // 주소의 패턴 ":address:"을 읽어 들임: 이미 완성된 코드이므로 아래 address를 바로 활용하면 됨
        while ((address = UI.scan.findInLine(":.*:")) == null)
            UI.scan.nextLine();  // 현재 행에 주소가 없다면 그 행을 스킵함
        address = address.substring(1, address.length() - 1); // 주소 양 끝의 ':' 문자 삭제

        System.out.println(name + " " + id + " " + weight + " " + married + " :" + address + ":");
        // TODO: "이름 id 몸무게 혼인여부 :주소:"를 출력함
    }

    public static void readLine() {
        String name = UI.getNext("name? "); // "name? "을 출력한 후 이름을 입력 받음
        // TODO: 실행결과("name: p")처럼 출력
        System.out.println("name: " + name);

        int id = UI.getInt("id? ");         // "id? "을 출력한 후 id을 입력 받음
        // TODO: 실행결과("id: 1")처럼 출력
        System.out.println("id: " + id);


        String address = UI.getNextLine("address? ");// "address? " 출력 후 한줄 전체 입력받음
        System.out.println("address :" + address + ":");
        // TODO: 문자열 address를 실행결과("address :seoul gangnam:")처럼 출력하라.

    }

    public static void printBinStr(int v) {
        String s = Integer.toBinaryString(v);
        for (int i = 0; i < (32 - s.length()); ++i)
            System.out.print('0');
        System.out.println(s);
    }

    public static void operator() {
        int b = 0b11111111_00000000_11111111_11111111;
        printBinStr(b);
        printBinStr(b << 4);

        // TODO: 변수 b를 왼쪽으로 4비트 이동시킨 값을 출력하라.

        System.out.println();
        b = 0b11111111_00000000_00000000_11111111;
        printBinStr(b);
        printBinStr(b >> 4);
        printBinStr(b >>> 4);

        // TODO: 변수 b를 4비트 산술적 오른쪽 시프트를 한 값을 출력하라.

        // TODO: 변수 b를 4비트 논리적 오른쪽 시프트를 한 값을 출력하라.
    }

    public static void switchCase() {
        String menuStr =
                "********* Switch Menu *********\n" +
                        "* 0.exit 1.output 2.readToken *\n" +
                        "* 3.readLine 4.operator       *\n" +
                        "*******************************\n";
        while (true) {

            String menu = UI.getNext("\n" + menuStr + "menu item string? ");
            //System.out.println("Selected menu: " + menu);
            // menu는 메뉴항목 번호가 아닌 메뉴항목 단어를 직접 입력 받은 것임
            // TODO: Ch2.run()을 참조하여 switch 문장을 이용하여 상응하는 함수를 호출하라.
            //      단, 입력된 메뉴항목이 정수가 아니라 문자열(menu)임을 명심하라.
            //      즉, case 문장이 정수가 아니라 문자열과 비교 되어야 한다.

            switch (menu) {
                case "exit":
                    return;
                case "output":
                    output();
                    break;
                case "readToken":
                    readToken();
                    break;
                case "readLine":
                    readLine();
                    break;
                case "operator":
                    operator();
                    break;
            }
        }
    }
}

//===============================================================================
//class Ch3
//===============================================================================
class Ch3
{
    public static void run() {
        final int MENU_COUNT = 4;
        String menuStr =
                "************* Ch3 Menu **************\n" +
                        "* 0.Exit 1.array 2.exception 3.game *\n" +
                        "*************************************\n";

        while (true) {
            int menuItem = UI.selectMenu(menuStr, MENU_COUNT);
            // TODO: 입력 받은 memnuItem 값을 프로그램 실행결과처럼 출력하라. 예) menu item: 1
            //System.out.print("menu item: " + menuItem);
            switch (menuItem) {
                case 0:
                    return;
                case 1:
                    array();
                    break;
                case 2:
                    exception();
                    break;
                case 3:
                    game();
                    break;
            }
        }

        // TODO: Ch2::run() 함수를 참고하여 while문과 switch문을 작성하라.
        //       switch에서는 아래의 상응하는 함수를 호출하고, MENU_COUNT도 적절히 수정하라.
    }

    public static void array() {
        double arr1[][] = { {0}, {1,2}, {3,4,5} };
        printArray(arr1);
        double arr2[][] = { {0,1,2,3}, {4,5,6}, {7,8}, {9} };
        printArray(arr2);

        var arr3 = inputArray();
        printArray(arr3);
        arr3 = inputArray();
        printArray(arr3);
    }
    public static void printArray(double arr[][]) {
        System.out.println("arr length: " + arr.length);
        for(int i=0; i<arr.length; i++){
            System.out.print("arr[" + i + "]");
            for(var s : arr[i]){
                System.out.print(" " + s);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static double[][] inputArray(){
        double arr[][];
        int row = UI.getPosInt("array rows? ");
        arr = new double[row][];
        for(int i=0; i<row; i++){
            arr[i] = new double[i+1];
            System.out.print("input " + (i+1) + " doubles for row " + i + ": ");
            for(int k=0; k<i+1; k++){
                arr[i][k] = UI.scan.nextDouble();
            }
        }
        System.out.println();

        return arr;
    }

    //------------------------------------------------------------------------
    // Exception
    //------------------------------------------------------------------------
    static Random random = null; // 난수 발생기

    public static void exception() {
        var random = new Random(UI.getInt("seed for random number? "));// 난수 발생기
        String str;
        int i;
        int arr[] = null;
        while(true){
            try{
                str = UI.getNext("array[] size? ");
                i = Integer.parseInt(str);   // 문자열 숫자를 정수로 변환: "123" -> 123
                arr = new int[i];
                break;
            }catch(NumberFormatException e){
                System.out.println(e);
            }catch(NegativeArraySizeException e){
                System.out.println(e);
            }catch(ArrayIndexOutOfBoundsException e){
                System.out.println(e);
            }
        }
        for (i = 0; i < arr.length; ++i) // arr[] 전체를 난수 값으로 초기화
            arr[i] = random.nextInt(3);  // [0,1,2] 범위의 난수 발생
        System.out.print("array[]: { ");
        for (var v : arr)                // 배열 전체 출력
            System.out.print(v+" ");     // 각각의 v=arr[i] 원소 값을 출력함
        System.out.println("}");


        while(true){
            try{
                i = UI.getPosInt("array[] index? ");
                System.out.println("array["+i+"] = "+arr[i]);
                break;
            }catch(ArrayIndexOutOfBoundsException e){
                System.out.println(e);
            }
        }
        int numerator   = UI.getIndex("numerator   index? ", arr.length); // 분자 index

        while(true){
            try{
                int denominator = UI.getIndex("denominator index? ", arr.length); // 분모 index
                System.out.println(arr[numerator]+" / "+arr[denominator]+" = "
                        +(arr[numerator] / arr[denominator]));
                break;
            }catch(ArithmeticException e){
                System.out.println(e);
            }
        }
        System.out.println("makeArray(): first");
        while(true){
            try{
                arr = makeArray();
                break;
            }catch(OutOfMemoryError e){
                System.out.println(e);
            }
        }
        System.out.println("makeArray(): second");
        while(true) {
            try {
                arr = makeArray();
                System.out.println("array length = " + arr.length);
                break;
            }catch(NullPointerException e2){
                System.out.println("NullPointerException");
            }
        }
    }

    // tag 0: OutOfMemoryError, 1: return null pointer, 2: return normal array
    public static int[] makeArray() {
        int tag = UI.getPosInt("makeArray tag[0,1,2]? ");
        return (tag == 0)? new int[0x7fffffff]: (tag == 1)? null: new int[10];
    }

    public static void game() {
        final int USER = 0;     // 상수 정의
        final int COMPUTER = 1;
        String MJBarray[] = { "m", "j", "b" }; // 묵(m) 찌(j) 빠(b) 문자열을 가진 배열
        System.out.println("Start the MUK-JJI-BBA game.");
        // 난수 발생기
        random = new Random(UI.getInt("seed for random number? "));
        // 누가 우선권을 가졌는지 저장하고 있음, USER:사용자 우선권, COMPUTER:computer 우선권
        int priority = USER;
        String priStr[] = { "USER", "COMPUTER"}; // 우선권을 화면에 출력할 때 사용할 문자열

        while(true) {
            System.out.println();
            System.out.println(priStr[priority] + " has the higher priority.");

            String user = UI.getNext("m(muk), j(jji), b(bba) or stop? ");

            if(user.equals("stop"))
                break;
            if((!user.equals("m") && !user.equals("j") && !user.equals("b"))) {
                System.out.println("Select one among m, j, b.");
                continue;
            }
            // [0,1,2] 난수를 이용하여 COMPUTER가 묵찌빠 중 하나를 선택함
            String computer = MJBarray[random.nextInt(MJBarray.length)];
            if(!user.equals(computer)) {
                System.out.println("User = " + user + ", Computer = " + computer + ", " + "SAME.");
                if(user.equals("j") && computer.equals("b")) {
                    priority = USER;
                }
                else if(user.equals("b") && computer.equals("m")) {
                    priority = USER;
                }
                else if(user.equals("m") && computer.equals("j")) {
                    priority = USER;
                }
                else if(user.equals("b") && computer.equals("j")) {
                    priority = COMPUTER;
                }
                else if(user.equals("m") && computer.equals("b")) {
                    priority = COMPUTER;
                }
                else if(user.equals("j") && computer.equals("m")) {
                    priority = COMPUTER;
                }
            }
            else
                System.out.println("User = " + user + ", Computer = " + computer + ", " + (priStr[priority]) + " WINs.");
//             이후 user와 computer 문자열을 비교하여 같으면
//                 // user와 computer 두 문자열 비교하는 방법: if (user.equals(computer))
//                 // 우선권을 가진 사람의 묵찌빠와 동일한 묵찌빠를 상대방이 냈을 경우 우선권을 가진 사람이 승리함
//                 우선권을 가진 자(priStr[priority])가 " WINs." 했다고 출력하고
//             같지 않으면 (비겼음)
//                 "SAME." 출력
//                 현 상황을 (가위바위보)라 가정하고 누가 이겼는지 판단하여 우선권(priority)을 가진자 결정
//                 즉, priority 값을 가위바위보상 이긴 자(USER 또는 COMPUTER)로 변경함
//                 (아주 긴 조건문이 될 것임)
            // 가위 -> 보 j > b
            // 보 -> 주먹 b > m
            // 주먹 -> 가위 m > j
        }
    }
}