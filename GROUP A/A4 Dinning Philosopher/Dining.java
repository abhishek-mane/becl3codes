import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class Dining {

	static FileInputStream input;
	static StringTokenizer sharedMemory;

	public static void main(String[] args) throws IOException {
		Lock forks[] = new ReentrantLock[5];

		for (int i = 0; i < 5; i++) {
			forks[i] = new ReentrantLock();
		}

		try {
			input = new FileInputStream("/home/hduser/eclipse-mars-workspace/Dinning/bin/sensorData.txt");
		} catch (FileNotFoundException ex) {
			System.out.println("sensorData.txt not found.");
		}
		
		String sensorData = "";
		int sensorDataSize = input.available();
		for (int i = 0; i < sensorDataSize; i++) {
			sensorData += (char) input.read();
		}
		sharedMemory = new StringTokenizer(sensorData, ",");

		Thread p1 = new Thread(new Philosopher(forks[4], forks[0], "first", sharedMemory));
		Thread p2 = new Thread(new Philosopher(forks[0], forks[1], "second", sharedMemory));
		Thread p3 = new Thread(new Philosopher(forks[1], forks[2], "third", sharedMemory));
		Thread p4 = new Thread(new Philosopher(forks[2], forks[3], "fourth", sharedMemory));
		Thread p5 = new Thread(new Philosopher(forks[3], forks[4], "fifth", sharedMemory));

		p1.start();
		p2.start();
		p3.start();
		p4.start();
		p5.start();

	}

}

class Philosopher implements Runnable {

	Lock leftFork;
	Lock rightFork;
	String name;
	StringTokenizer sharedMemory;
	MongoClient mongoClient;
	static DB db;
	static DBCollection coll;
	String sensorData = null;

	public Philosopher(Lock leftFork, Lock rightFork, String name, StringTokenizer sharedMemory) {
		this.leftFork = leftFork;
		this.rightFork = rightFork;
		this.name = name;
		this.sharedMemory = sharedMemory;
	}

	@Override
	public void run() {
		try {
			this.think();
			this.eat();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void think() throws Exception {
		leftFork.lock();
		rightFork.lock();
		System.out.println("Thread " + this.name + " going inside critical section.");
	}

	private void eat() throws Exception {

		sensorData = sharedMemory.nextToken();
		mongoClient = new MongoClient("localhost");// ip of server
		db = mongoClient.getDB("mydb");
		coll = db.getCollection("mycol");
		System.out.println(name + " Inside critical secion.");
		BasicDBObject doc = new BasicDBObject(name, sensorData);
		coll.insert(doc);
		System.out.println(name + " executed critical section.");
		leftFork.unlock();
		rightFork.unlock();
	}

}