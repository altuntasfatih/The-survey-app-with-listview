package com.example.android.proje;


public class Questions {
    private String id;
    private int number;
    private String question;
    private int type;
    private int result=-1;
    public Questions(int number, String question){
        super();
        this.number=number;
        this.question=question;

    }
    public Questions(String id, int number, String question, int type){
        super();
        this.id=id;
        this.number=number;
        this.question=question;
        this.type=type;
    }
    public void setNumber(int number)
    {
        this.number=number;
    }
    public int getResult()
    {
        return this.result;
    }
    public void setResult(int result)
    {
        this.result=result;
    }
    public int getNumber()
    {
        return this.number;
    }
    public String getQuestion()
    {
        return this.question;
    }
    public String getId()
    {
        return this.id;
    }
    public int  getType()
    {
        return this.type;
    }
    public String toString()
    {


        //return id+"_"+number+"1:"+choose.check(this.getNumber() * 10 + 1)+"2:"+choose.check(this.getNumber() * 10 + 2)+"2:"+choose.check(this.getNumber() * 10 + i)+"3:"+choose.check(this.getNumber() * 10 + i)+"4:"+choose.check(this.getNumber() * 10 + i);
        int selectedID = 0;

        return id+"_"+number+"result"+result;
    }
}
