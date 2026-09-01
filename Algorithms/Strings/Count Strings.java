//https://www.hackerrank.com/challenges/count-strings/problem?isFullScreen=true
import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {
    
    static final int MOD = 1000000007;

    static class State {
        int id;
        List<State> epsilons = new ArrayList<>();
        State a = null;
        State b = null;
        State(int id) { this.id = id; }
    }

    static class NFA {
        State start, end;
        NFA(State start, State end) {
            this.start = start;
            this.end = end;
        }
    }

    static class Matrix {
        long[][] mat;
        int size;
        Matrix(int size) {
            this.size = size;
            mat = new long[size][size];
        }
        Matrix multiply(Matrix other) {
            Matrix res = new Matrix(size);
            for (int i = 0; i < size; i++) {
                for (int k = 0; k < size; k++) {
                    if (mat[i][k] == 0) continue;
                    for (int j = 0; j < size; j++) {
                        res.mat[i][j] = (res.mat[i][j] + mat[i][k] * other.mat[k][j]) % MOD;
                    }
                }
            }
            return res;
        }
        Matrix power(int p) {
            Matrix res = new Matrix(size);
            for (int i = 0; i < size; i++) res.mat[i][i] = 1;
            Matrix base = this;
            while (p > 0) {
                if ((p & 1) == 1) res = res.multiply(base);
                base = base.multiply(base);
                p >>= 1;
            }
            return res;
        }
    }

    static int stateCounter = 0;

    public static int countStrings(String r, int l) {
        stateCounter = 0;
        NFA nfa = parseRegex(r);
        
        // Subset Construction: NFA to DFA
        List<Set<Integer>> dfaStates = new ArrayList<>();
        Map<Set<Integer>, Integer> dfaStateMap = new HashMap<>();
        
        Set<Integer> startClosure = getEpsilonClosure(Collections.singleton(nfa.start));
        dfaStates.add(startClosure);
        dfaStateMap.put(startClosure, 0);
        
        List<int[]> dfaTransitions = new ArrayList<>();
        
        int head = 0;
        while (head < dfaStates.size()) {
            Set<Integer> currentDfaState = dfaStates.get(head);
            int[] trans = new int[2]; // 0 for 'a', 1 for 'b'
            
            Set<Integer> onA = getEpsilonClosure(move(currentDfaState, 'a'));
            if (!onA.isEmpty()) {
                if (!dfaStateMap.containsKey(onA)) {
                    dfaStateMap.put(onA, dfaStates.size());
                    dfaStates.add(onA);
                }
                trans[0] = dfaStateMap.get(onA);
            } else {
                trans[0] = -1;
            }
            
            Set<Integer> onB = getEpsilonClosure(move(currentDfaState, 'b'));
            if (!onB.isEmpty()) {
                if (!dfaStateMap.containsKey(onB)) {
                    dfaStateMap.put(onB, dfaStates.size());
                    dfaStates.add(onB);
                }
                trans[1] = dfaStateMap.get(onB);
            } else {
                trans[1] = -1;
            }
            
            dfaTransitions.add(trans);
            head++;
        }
        
        // Build Adjacency Matrix
        int dfaSize = dfaStates.size();
        Matrix adj = new Matrix(dfaSize);
        for (int i = 0; i < dfaSize; i++) {
            int toA = dfaTransitions.get(i)[0];
            int toB = dfaTransitions.get(i)[1];
            if (toA != -1) adj.mat[i][toA]++;
            if (toB != -1) adj.mat[i][toB]++;
        }
        
        // Matrix Exponentiation
        Matrix resultMat = adj.power(l);
        
        // Sum paths ending in accepting DFA states
        long totalValidStrings = 0;
        for (int i = 0; i < dfaSize; i++) {
            Set<Integer> stateSet = dfaStates.get(i);
            if (stateSet.contains(nfa.end.id)) {
                totalValidStrings = (totalValidStrings + resultMat.mat[0][i]) % MOD;
            }
        }
        
        return (int) totalValidStrings;
    }

    // --- Parser & Automata Helpers ---
    
    static Map<Integer, State> allStates = new HashMap<>();

    static String insertConcat(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            sb.append(c);
            if (i < s.length() - 1) {
                char next = s.charAt(i + 1);
                boolean cIsCharOrStarOrClose = (c == 'a' || c == 'b' || c == '*' || c == ')');
                boolean nextIsCharOrOpen = (next == 'a' || next == 'b' || next == '(');
                if (cIsCharOrStarOrClose && nextIsCharOrOpen) {
                    sb.append('.'); // Explicit concatenation operator
                }
            }
        }
        return sb.toString();
    }

    static int precedence(char c) {
        if (c == '*') return 3;
        if (c == '.') return 2;
        if (c == '|') return 1;
        return 0;
    }

    static String infixToPostfix(String s) {
        s = insertConcat(s);
        StringBuilder postfix = new StringBuilder();
        Stack<Character> ops = new Stack<>();
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == 'a' || c == 'b') {
                postfix.append(c);
            } else if (c == '(') {
                ops.push(c);
            } else if (c == ')') {
                while (!ops.isEmpty() && ops.peek() != '(') {
                    postfix.append(ops.pop());
                }
                if (!ops.isEmpty()) ops.pop();
            } else {
                while (!ops.isEmpty() && precedence(ops.peek()) >= precedence(c)) {
                    postfix.append(ops.pop());
                }
                ops.push(c);
            }
        }
        while (!ops.isEmpty()) {
            postfix.append(ops.pop());
        }
        return postfix.toString();
    }

    static NFA parseRegex(String s) {
        allStates.clear();
        String postfix = infixToPostfix(s);
        Stack<NFA> stack = new Stack<>();
        
        for (int i = 0; i < postfix.length(); i++) {
            char c = postfix.charAt(i);
            if (c == 'a' || c == 'b') {
                State start = newState();
                State end = newState();
                if (c == 'a') start.a = end;
                else start.b = end;
                stack.push(new NFA(start, end));
            } else if (c == '*') {
                NFA n = stack.pop();
                State start = newState();
                State end = newState();
                start.epsilons.add(n.start);
                start.epsilons.add(end);
                n.end.epsilons.add(n.start);
                n.end.epsilons.add(end);
                stack.push(new NFA(start, end));
            } else if (c == '.') {
                NFA n2 = stack.pop();
                NFA n1 = stack.pop();
                n1.end.epsilons.add(n2.start); // Concatenate N1 -> N2
                stack.push(new NFA(n1.start, n2.end));
            } else if (c == '|') {
                NFA n2 = stack.pop();
                NFA n1 = stack.pop();
                State start = newState();
                State end = newState();
                start.epsilons.add(n1.start);
                start.epsilons.add(n2.start);
                n1.end.epsilons.add(end);
                n2.end.epsilons.add(end);
                stack.push(new NFA(start, end));
            }
        }
        return stack.isEmpty() ? null : stack.pop();
    }

    static State newState() {
        State s = new State(stateCounter++);
        allStates.put(s.id, s);
        return s;
    }

    static Set<Integer> getEpsilonClosure(Set<State> states) {
        Set<Integer> closure = new HashSet<>();
        Queue<State> queue = new LinkedList<>(states);
        for (State s : states) closure.add(s.id);
        
        while (!queue.isEmpty()) {
            State curr = queue.poll();
            for (State next : curr.epsilons) {
                if (!closure.contains(next.id)) {
                    closure.add(next.id);
                    queue.add(next);
                }
            }
        }
        return closure;
    }

    static Set<State> move(Set<Integer> stateIds, char c) {
        Set<State> nextStates = new HashSet<>();
        for (int id : stateIds) {
            State s = allStates.get(id);
            if (c == 'a' && s.a != null) nextStates.add(s.a);
            if (c == 'b' && s.b != null) nextStates.add(s.b);
        }
        return nextStates;
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int t = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, t).forEach(tItr -> {
            try {
                String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                String r = firstMultipleInput[0];

                int l = Integer.parseInt(firstMultipleInput[1]);

                int result = Result.countStrings(r, l);

                bufferedWriter.write(String.valueOf(result));
                bufferedWriter.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}
