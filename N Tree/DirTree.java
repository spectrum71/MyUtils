import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class DirTree {
	Folder root;
	
	public DirTree() {
		root = new Folder("root",null);
		
	}
	public void addFolder(String data, String parent) {
		addFolder(data,parent,root);
	}
	public void addFolder(String data, String parent, Data node) {
		if(node instanceof Folder&&node.name().equals(parent)) {
			Folder fold = (Folder) node;
			if(node.type().equals("folder")) fold.addData(new Folder(data,fold));
			else {
				List<Data> parentDir = fold.clearDir();
				String parentType = fold.clearType();
				fold.addData(new Folder(data, fold, parentDir, parentType));
			}
			return;
		}
		List<Data> children = null;
		if(node instanceof Folder) children = ((Folder)node).dir();
		if(children!=null&&!children.isEmpty()) {
			for(Data i : children) {
				if(i instanceof Folder)addFolder(data,parent,i);
			}
		}
	}
	public void addFile(String data, String ext, String parent) {
		addFile(data,ext,parent,root);
	}
//	public void addFile(String data, String ext, String parent,Folder node) {
//		Stack<Folder> path = new Stack<>();
//		Stack<Integer> index = new Stack<>();
//		Folder mainFold = null;
//		boolean mainFound = false;
//		Folder firstBackUp = null;
//		Folder secondBackUp = null;
//		Folder fold = (Folder) node;
//		path.push(fold);
//		while(!path.isEmpty()) {
//			if(path.peek().name().equals(data)){
//				mainFold = path.pop();
//				mainFound = true;
//				if(mainFold.type()==null||mainFold.type().equals(ext)) break;
//				else mainFold = null;
//			}
//			if(path.peek() instanceof Folder) {
//				index.push(0);
//				
//				if(fold.dir().isEmpty()||fold.clearType()) {
//					
//				}
//				path.push(fold.dir().get(index.peek()));
//			}
//		}
//		
//	}

}

abstract class Data implements Comparable<Data>{
	private String name;
	private Folder parent;
	private int size;
	private String type;
	public Data(String name, Folder parent) {
		this.name=name;
		this.parent=parent;
		size=1;
	}
	public String name() {
		return name;
	}
	public Folder parent() {
		return parent;
	}
	public int size() {
		return size;
	}
	public void changeSize(int newSize) {
		size=newSize;
	}
	public String type() {
		return type;
	}
	public void changeType(String newType) {
		type = newType;
	}
	public int compareTo(Data other) {
		return this.name.compareTo(other.name);
	}
	
}

class Folder extends Data{
	private List<Data> dir;
	private String dirType;
	public Folder(String name, Folder parent) {
		super(name,parent);
		dir = new ArrayList<>();
		dirType = null;
		changeType("folder");
	}
	public Folder(String name, Folder parent, List<Data> dir, String dirType) {
		this(name,parent);
		this.dir=dir;
		this.dirType=dirType;
	}
	public void addData(Data data) {
		boolean added = false;
		for(int i=0;i<dir.size()&&added;i++) {
			if(dir.get(i).compareTo(data)>0) {
				dir.add(i, data);
				added = true;
			}
		}
		if(!added)dir.add(data);
	}
	public List<Data> clearDir(){
		List<Data> files = dir;
		dir = new LinkedList<>();
		return files;
	}
	public List<Data> dir(){
		return dir;
	}
	public String clearType() {
		String prev = dirType;
		dirType = "folder";
		return prev;
	}
}

class File extends Data{
	String ext;
	public File(String name, Folder parent, String ext, int size) {
		super(name,parent);
		changeSize(size);
		changeType(ext);
	}
}