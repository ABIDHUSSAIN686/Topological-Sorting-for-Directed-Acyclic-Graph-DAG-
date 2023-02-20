import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
class Topological_Graph
{
    //variable for vertices
    private int V;
    private LinkedList<Integer> Array[]; 
  
    // Parametrized constructor taking vertices as input
    public Topological_Graph(int v)
    {
        V = v;
        Array = new LinkedList[v];
        for (int i=0; i<v; ++i)
            Array[i] = new LinkedList();
    }
  
    //Adding the Edge to the Graph
    public void addEdge(int v,int w) { 
        Array[v].add(w); 
    }
  
    //Recursive call of the Function
    public void Topological_Sort_Helper(int v, boolean visited_nodes[],Stack stack_nodes){
        visited_nodes[v] = true; // marking the visited_nodes nodes as true
        Integer i;
        Iterator<Integer> temp = Array[v].iterator();
        while (temp.hasNext())
        {
            i = temp.next();
            if (!visited_nodes[i])
                Topological_Sort_Helper(i, visited_nodes, stack_nodes);
        }
        // Pushing  current vertex to stack
        stack_nodes.push(new Integer(v));
    }
  
    // Function for the Topological Sort. Uses the recusive call of 'Topological_Sort_Helper'
    public void Topological_Sort(){
        Stack stack_nodes = new Stack();
        boolean visited_nodes[] = new boolean[V];
        for (int i = 0; i < V; i++)
            visited_nodes[i] = false;
        for (int i = 0; i < V; i++)
            if (visited_nodes[i] == false)
                Topological_Sort_Helper(i, visited_nodes, stack_nodes);
        // Printing the Sorted graph
        while (stack_nodes.empty()==false)
            System.out.print(stack_nodes.pop() + " ");
        System.out.print("\n");
    }
    // recursive calling the function for checking cycle
    public boolean Check_Cyclic(int i, boolean[] visited,boolean[] temp_array)
    {
        if (temp_array[i])
            return true;
        if (visited[i])
            return false;
        visited[i] = true;
        temp_array[i] = true;
        List<Integer> children = Array[i];
        for (Integer c: children)
            if (Check_Cyclic(c, visited, temp_array))
                return true;
                 
        temp_array[i] = false;
 
        return false;
    }
 
    // Return true if it has cycle
    public boolean isCyclic()
    {
        boolean[] visited = new boolean[V];
        boolean[] temp_array = new boolean[V];
        for (int i = 0; i < V; i++)
            if (Check_Cyclic(i, visited, temp_array))
                return true;
        return false;
    }


};

// Main class
class main{
	public static void main(String[] args) {
		System.out.println(args[0]);
		// List for storing the vertices no 
		List<String> Vertices_list = new ArrayList<String>();
		// List for storing the value and edges 
		List<String> KeyValuePair_list = new ArrayList<String>();
		String Value = "";
		boolean check = true;
		try {
			File myObj = new File(args[0]);// Reading the File
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				if (data.contains("----------------")) {
					check = true;
					KeyValuePair_list.add(Value);
					Value = "";
				}
				if (check == false) {
					if (data.trim().contains("}")) {
						Value += data.trim();
					} else {
						Value += data.trim() + ",";
					}
				}
				if (data.contains("**")) {
					int v1 = data.indexOf('{');
					int v2 = data.indexOf('}');
					Vertices_list.add(data.substring(v1, v2 + 1).trim());
				} else if (data.contains("(u, v)")) {
					check = false;
					Value += "{";
				}
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		System.out.println("Topological Orders:");
		int count = 0;
		for (int i = 0; i < Vertices_list.size(); i++) {
			count = 0;
			int len = Vertices_list.get(i).length();
			for (int j = 0; j < len; j++) {
				char temp = Vertices_list.get(i).charAt(j);
				if (temp == ' ') {
						
                    count++;
            }
			}
			int len1 = KeyValuePair_list.get(i).length();
			int flag = 1;
			int a = 0, b = 0;
			// Making the new Graph
			Topological_Graph g = new Topological_Graph(count);
			for (int j = 0; j < len1; j++) {
				char temp1 = KeyValuePair_list.get(i).charAt(j);
				if (temp1 != '{' && temp1 != '}' && temp1 != ' ' && temp1 != '(' && temp1 != ')'&& temp1 != ',') {

					if (flag == 1){
                                                
						a=temp1-48;
                                                if(j+1<len1){
                                                temp1=KeyValuePair_list.get(i).charAt(j+1);
                                              
                                               if(temp1!=',' && temp1 != ' ' && temp1 != '(' && temp1 != ')' && temp1 != '{' && temp1 != '}' ){
                                                     // System.out.println(temp1);
                                                    a=(a*10);
                                                    int c=temp1-48;
                                                    a=a+c;
                                                    j=j+1;
                                                }
                                                }
						flag++;
					} 
					else if (flag == 2){
						b = temp1-48;
                                                 if(j+1<len1){
                                                temp1=KeyValuePair_list.get(i).charAt(j+1);
                                                 if(temp1!=',' && temp1 != ' ' && temp1 != '(' && temp1 != ')' && temp1 != '{' && temp1 != '}'){   
                                                    b=(b*10);
                                                    int c=temp1-48;
                                                    b=b+c;
                                                    j=j+1;
                                                }
                                                 }
						
                                              g.addEdge(a, b);
						flag = 1;
						a=0;
						b=0;
						
					}
					count++;
				}
			}
			System.out.print("G"+(i+1)+":   ");
			if(i+1==7){
				System.out.print(""+(i+1)+"-> ");
			}

			if(g.isCyclic())
                       System.out.println("No more in-degree 0 vertex; not an acyclic graph");
                        else{
					// Calling the Sorting Function
					g.Topological_Sort();
			}
			
		}

	}
}
