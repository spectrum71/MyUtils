import java.util.LinkedList;
import java.util.List;

/**
 * Class ini adalah implementasi dari tree yang diminta soal.
 * Merupakan struktur data dengan elemen yang sudah tersortir,
 * sehingga memudahkan pencarian
 * @param <E> element dari tree, harus comparable
 */
public class MesoAVL<E extends Comparable<? super E>> {
	private TreeNode<E> root;
	//constructor tree
	public MesoAVL() {
		root = null;
	}
	/**
	 * Method untuk mengecek apakah tree kosong atau tidak
	 * @return apakah root null atau tidak
	 */
	public boolean isEmpty() {
		return root == null;
	}
	/**
	 * Method untuk menambah elemen. Terlebih
	 * dahulu memanggil method find untuk mengkonfirmasi
	 * keberadaan element yang ingin di add. Jika element
	 * ada maka tidak akan ditambah
	 * @param element nilai yang ingin ditambah
	 * @return keberhasilan penambahan element
	 */
	public boolean add(E element) {
		boolean newElem = !find(element);
		if(newElem)root = addHelper(element, root);
		return newElem;
	}
	/**
	 * Tail method dari add, berfungsi mencari tempat node
	 * baru secara rekursif, dan dilakukan proses rebalancing
	 * sesuai dengan prosedur AVL
	 * @param element nilai dari node
	 * @param t node yang diperiksa
	 * @return new node untuk tempat node baru, lainnya node itu sendiri
	 */
	private TreeNode<E> addHelper(E element, TreeNode<E> t){
		if(t==null) {
			t = new TreeNode<E>(element);
			return t;
		}
		if(element.compareTo(t.element)<0) t.left=addHelper(element,t.left);
		else if(element.compareTo(t.element)>0) t.right=addHelper(element,t.right);
		else return t;
		
		//update height dari node
		t.height = 1 + Math.max(treeSizer(t.left),treeSizer(t.right));

		//hitung balance factor dari node yang bersangkutan
	    //kemudian bandingkan dengan 4 kasus
		int balance = getBalance(t);
		
		// berat kiri dan elemen masuk di cabang kiri subtree kiri : single right rotate
		if (balance > 1 && element.compareTo(t.left.element) < 0)
			return rightRotate(t);
		
		// berat kanan dan elemen masuk di cabang kanan subtree kanan : single left rotate
		if (balance < -1 && element.compareTo(t.right.element) > 0)
			return leftRotate(t);
		
		// berat di kiri dan elemen masuk di cabang kanan subtree kiri : double left-right rotate
		if (balance > 1 && element.compareTo(t.left.element) > 0)
		{
			t.left = leftRotate(t.left);
			return rightRotate(t);
		}
		
		// berat di kanan dan elemen masuk di cabang kiri subtree kanan : double right-left rotate
		if (balance < -1 && element.compareTo(t.right.element) < 0)
		{
			t.right = rightRotate(t.right);
			return leftRotate(t);
		}
		return t;
	}
	int treeSizer(TreeNode<E> t) {
		if(t==null) return -1;
		return t.height;
	}
	TreeNode<E> rightRotate(TreeNode<E> y)
	 {
		TreeNode<E> x = y.left;
		TreeNode<E> T2 = x.right;

	     // Perform rotation
	     x.right = y;
	     y.left = T2;

	     // Update heights
	     y.height = Math.max(treeSizer(y.left), treeSizer(y.right)) + 1;
	     x.height = Math.max(treeSizer(x.left), treeSizer(x.right)) + 1;

	     // Return new root
	     return x;
	 }

	 // A utility function to left rotate subtree rooted with x
	 // See the diagram given above.
	TreeNode<E> leftRotate(TreeNode<E> x)
	 {
		TreeNode<E> y = x.right;
		TreeNode<E> T2 = y.left;

	     // Perform rotation
	     y.left = x;
	     x.right = T2;

	     //  Update heights
	     x.height = Math.max(treeSizer(x.left), treeSizer(x.right)) + 1;
	     y.height = Math.max(treeSizer(y.left),treeSizer(y.right)) + 1;

	     // Return new root
	     return y;
	 }

	 /**
	  * Method untuk mendapatkan balance flag dari sebuah node
	  * >1 : berat di anak kiri
	  * <-1 : berat di anak kanan
	  * @param N : node yang diukur
	  * @return angka balance flag
	  */
	 int getBalance(TreeNode<E> N)
	 {
	     if (N == null)
	         return 0;
	     return treeSizer(N.left) - treeSizer(N.right);
	 }
	/**
	 * Method untuk mencari element terkecil dri tree
	 * @return element terkecil dari tree
	 */
	public E findMin() {
		return findMinHelper(root).element;
	}
	/**
	 * Method tail dari getMin untuk mencari node dengan element terkecil
	 * secara rekursif
	 * @param t node yang diperiksa
	 * @return node dengan kunci terkecil atau null jika tree kosong
	 */
	private TreeNode<E> findMinHelper(TreeNode<E> t){
		if(t.left!=null) return findMinHelper(t.left);
		return t;
	}
	/**
	 * Method untuk mencari element terbesar dari tree
	 * @return element terbesar dari tree
	 */
	public E findMax() {
		TreeNode<E> t = root;
		while(t.right!=null)t=t.right;
		return t.element;
	}
	/**
	 * Method untuk meremove element dari tree. Terlebih dahulu dicek
	 * keberadaan element yang dimaksud
	 * @param element nilai yang dimaksud
	 * @return keberhasilan penghapusan element
	 */
	public boolean remove(E element) {
		boolean elemExist = find(element);
		if(elemExist)root = removeHelper(element, root);
		return elemExist;
	}
	/**
	 * Method tail untuk remove. Mencari node yang dimaksud secara rekursif.
	 * Tergantung dari jumlah anak, proses penghapusannya :
	 * 0 : ganti dengan null
	 * 1 : jika tidak ada anak kanan, sambungkan parent ke anak kiri, dan
	 * sebaliknya
	 * 2 : ganti nilai element dengan nilai element terkecil dari subtree kanan 
	 * element, kemudian hapus nilai min dari sebtree sebelah kanan
	 * Kemudian akan dilakukan rebalancing sesuai kondisi heaight pada subtree AVL
	 * @param element nilai yang dimaksud
	 * @param t node yang diperiksa
	 * @return null ( node terhapus ), anak ( sambung langsung ) atau node itu
	 * sendiri ( bukan node yang diremove )
	 */
	private TreeNode<E> removeHelper(E element, TreeNode<E> t){
		if(t==null) return t;
		if(element.compareTo(t.element)<0)t.left=removeHelper(element, t.left);
		else if(element.compareTo(t.element)>0)t.right=removeHelper(element, t.right);
		else {
			if(t.left==null)return t.right;
			else if(t.right==null)return t.left;
			t.element = findMinHelper(t.right).element;
			t.right = removeMin(t.right) ;
		}
	     //update height dari node
	     t.height = Math.max(treeSizer(t.left), treeSizer(t.right)) + 1;

	     //hitung balance factor dari node yang bersangkutan
	     //kemudian bandingkan dengan 4 kasus
	     int balance = getBalance(t);

	     // berat kiri dan elemen masuk di cabang kiri subtree kiri : single right rotate
	     if (balance > 1 && getBalance(t.left) >= 0)
	         return rightRotate(t);

	     // berat di kiri dan elemen masuk di cabang kanan subtree kiri : double left-right rotate
	     if (balance > 1 && getBalance(t.left) < 0)
	     {
	         t.left = leftRotate(t.left);
	         return rightRotate(t);
	     }

	     // berat di kanan dan elemen masuk di cabang kanan subtree kanan : single left rotate
	     if (balance < -1 && getBalance(t.right) <= 0)
	         return leftRotate(t);

	     // berat di kanan dan elemen masuk di cabang kiri subtree kanan : double right-left rotate
	     if (balance < -1 && getBalance(t.right) > 0)
	     {
	         t.right = rightRotate(t.right);
	         return leftRotate(t);
	     }

	     return t;
	}
	/**
	 * Method tail untuk menghapus nilai terkecil dari suatu subtree
	 * @param t node yang diperiksa
	 * @return null jika node anak jika dihapus, node itu sendiri jika tidak
	 */
	private TreeNode<E> removeMin(TreeNode<E> t){
		if(t==null) return t;
		if(t.left!=null) {
			t.left = removeMin(t.left);
			return t;
		}
		else return t.right;
	}
	/**
	 * Method untuk mengecek keberadaan node dengan element yang dimaksud. Digunakan
	 * oleh banyak method lain
	 * @param element niai yang dimaksud
	 * @return keberadaan nilai
	 */
	public boolean find(E element){
		return findHelper(element, root);
	}
	/**
	 * Tail method find. Mencari node dengan element yang dimaksud secara rekursif
	 * @param element nilai yang dimaksud
	 * @param t node yang diperiksa
	 * @return true jika node ada, false jika tidak
	 */
	private boolean findHelper(E element, TreeNode<E> t) {
		if(t!=null) {
			if(t.element.equals(element)) return true;
			else if(element.compareTo(t.element)<0) return findHelper(element, t.left);
			return findHelper(element, t.right);
		}
		return false;	
	}
	/**
	 * Method untuk mendapat string ASCENDING
	 * @return string output sesuai format dari input
	 * ASCENDING
	 */
	public String getAscend() {
		List<TreeNode<E>> nodes = ascendSeq();
		String out = "";
		for(TreeNode<E> i : nodes) {
			out += i.element+";";
		}
		if(out.length()>1)return out.substring(0, out.length()-1);
		return out;
	}
	/**
	 * Method helper untuk mendapatkan linkedlist berupa
	 * urutan node sesuai urutan node terkecil ke terbesar
	 * @return linkedlist berupa urutan node
	 */
	public List<TreeNode<E>> ascendSeq() {
		List<TreeNode<E>> nodes = new LinkedList<>();
		assignNodesInOrder(nodes, root);
		return nodes;
	}
	/**
	 * Method untuk menambahkan node ke dalam list. Menggunakan transversal In Order
	 * @param list : list untuk menampung node
	 * @param root node yang ingin ditambahkan
	 */
	private void assignNodesInOrder(List<TreeNode<E>> list, TreeNode<E> root) {
		if(root!=null) {
			if(root.left!=null)assignNodesInOrder(list, root.left);
			list.add(root);
			if(root.right!=null)assignNodesInOrder(list, root.right);
		}
	}
	/**
	 * Method untuk mendapat string DESCENDING
	 * @return string output sesuai format dari input
	 * DESCENDING
	 */
	public String getDescend() {
		List<TreeNode<E>> nodes = descendSeq();
		String out = "";
		for(TreeNode<E> i : nodes) {
			out += i.element+";";
		}
		if(out.length()>1)return out.substring(0, out.length()-1);
		return out;
	}
	/**
	 * Method helper untuk mendapatkan linkedlist berupa
	 * urutan node sesuai urutan node terbesar ke terkecil
	 * @return linkedlist berupa urutan node
	 */
	public List<TreeNode<E>> descendSeq() {
		List<TreeNode<E>> nodes = new LinkedList<>();
		assignNodesInOrderRev(nodes, root);
		return nodes;
	}
	/**
	 * Method untuk menambahkan node ke dalam list. Menggunakan transversal reversed
	 * In Order
	 * @param list : list untuk menampung node
	 * @param root node yang ingin ditambahkan
	 */
	private void assignNodesInOrderRev(List<TreeNode<E>> list, TreeNode<E> root) {
		if(root!=null) {
			if(root.right!=null)assignNodesInOrderRev(list, root.right);
			list.add(root);
			if(root.left!=null)assignNodesInOrderRev(list, root.left);
		}
	}
	/**
	 * Method untuk mendapat string PREORDER
	 * @return string output sesuai format dari input
	 * PREORDER
	 */
	public String getPreOrder() {
		List<TreeNode<E>> nodes = preOrderSeq();
		String out = "";
		for(TreeNode<E> i : nodes) {
			out += i.element+";";
		}
		if(out.length()>1)return out.substring(0, out.length()-1);
		return out;
	}
	/**
	 * Method helper untuk mendapatkan linkedlist berupa
	 * urutan node sesuai urutan preorder
	 * @return linkedlist berupa urutan node
	 */
	public List<TreeNode<E>> preOrderSeq() {
		List<TreeNode<E>> nodes = new LinkedList<>();
		assignNodesPreOrder(nodes, root);
		return nodes;
	}
	/**
	 * Method untuk menambahkan node ke dalam list. Menggunakan transversal Preorder
	 * @param list : list untuk menampung node
	 * @param root node yang ingin ditambahkan
	 */
	private void assignNodesPreOrder(List<TreeNode<E>> list, TreeNode<E> root) {
		if(root!=null) {
			list.add(root);
			if(root.left!=null)assignNodesPreOrder(list, root.left);
			if(root.right!=null)assignNodesPreOrder(list, root.right);
		}
	}
	/**
	 * Method untuk mendapat string POSTORDER
	 * @return string output sesuai format dari input
	 * POSTORDER
	 */
	public String getPostOrder() {
		List<TreeNode<E>> nodes = postOrderSeq();
		String out = "";
		for(TreeNode<E> i : nodes) {
			out += i.element+";";
		}
		if(out.length()>1)return out.substring(0, out.length()-1);
		return out;
	}
	/**
	 * Method helper untuk mendapatkan linkedlist berupa
	 * urutan node sesuai urutan postorder
	 * @return linkedlist berupa urutan node
	 */
	public List<TreeNode<E>> postOrderSeq() {
		List<TreeNode<E>> nodes = new LinkedList<>();
		assignNodesPostOrder(nodes, root);
		return nodes;
	}
	/**
	 * Method untuk menambahkan node ke dalam list. Menggunakan transversal Postorder
	 * @param list : list untuk menampung node
	 * @param root node yang ingin ditambahkan
	 */
	private void assignNodesPostOrder(List<TreeNode<E>> list, TreeNode<E> root) {
		if(root!=null) {
			if(root.left!=null)assignNodesPostOrder(list, root.left);
			if(root.right!=null)assignNodesPostOrder(list, root.right);
			list.add(root);
		}
	}
}
/**
 * Class node untuk tree. Berisi reference left dan right, dan element
 * @param <E> tipe element, harus comparable
 */
class TreeNode<E extends Comparable<? super E>>{
	protected E element;
	protected TreeNode<E> left;
	protected TreeNode<E> right;
	protected int height;
	
	public TreeNode(E element, TreeNode<E> leftChild, TreeNode<E> rightChild) {
		this.element=element;
		this.left=leftChild;
		this.right=rightChild;
		height = 0;
	}
	
	public TreeNode(E element) {
		this(element, null, null);
	}
	
	public TreeNode() {
		this(null);
	}
}