import java.io.IOException;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class Main {
	public static void main(String[] args) throws IOException {
		// ler codigo -> passar para o lexico -> passar para o sintatico -> fazer a
		// traducao
		System.out.println("Digite o seu codigo:");

		// fazer leitura do codigo do usuario
		CharStream input = CharStreams.fromStream(System.in);

		MyErrorListener errorListener = new MyErrorListener();

		try {
			// instanciar o analisador lexico
			atribuicaoLexer lexer = new atribuicaoLexer(input);
			lexer.removeErrorListeners();
			lexer.addErrorListener(errorListener);

			// gerar os tokens
			CommonTokenStream tokens = new CommonTokenStream(lexer);

			// instanciar o analisador sintatico
			atribuicaoParser parser = new atribuicaoParser(tokens);
			parser.removeErrorListeners();
			parser.addErrorListener(errorListener);
			ParseTree tree = parser.comece();
			// Map<String,String> Tokens = new HashMap<String,String>();

			// Instanciar analisador semântico
			tokens.fill();
			for (Object token : tokens.getTokens()) {
				System.out.println(token);
			}

			// andar sobre a arvore de derivacao
			ParseTreeWalker walker = new ParseTreeWalker();

			tradutor tr = new tradutor();
			walker.walk(tr, tree);
		} catch (Exception e) {
		}
	}
}