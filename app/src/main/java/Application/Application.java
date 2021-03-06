package Application;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import Arvore.Arvore;
import Arvore.No;
import Entrada.Decisao;
import Utils.Helper;

public class Application {

    public Application() {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean eLinhaValida(Row linha) {
        return !Objects.isNull(linha.getCell(0)) && !linha.getCell(0).getStringCellValue().isEmpty();
    }

    public static Decisao criarDecisaoPorLinha(Row row) {
        Decisao decisao = new Decisao();
        Iterator<Cell> cellIterator = row.cellIterator();
        decisao.setNodeId(cellIterator.next().getStringCellValue());
        decisao.setTextoPrincipal(cellIterator.next().getStringCellValue());
        decisao.setDirecaoSim(cellIterator.next().getStringCellValue());
        decisao.setDirecaoNao(cellIterator.next().getStringCellValue());
        decisao.setTextoAjuda(cellIterator.next().getStringCellValue());
        return decisao;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Arvore processaListaDecisao(List<Decisao> listaDecisao) {
        int indiceRaiz = 1, indiceSeguinteRaiz = 2, indiceFinal = listaDecisao.size();
        Decisao decisaoInicial = listaDecisao.get(indiceRaiz);
        No raiz = Helper.criarNoRaiz(decisaoInicial.getNodeId(), decisaoInicial.getTextoPrincipal(), decisaoInicial.getTextoAjuda(), decisaoInicial.getDirecaoNao(), decisaoInicial.getDirecaoSim());
        Arvore arvoreDecisao = new Arvore(raiz);
        AtomicReference<No> no = new AtomicReference<No>();
        listaDecisao.subList(indiceSeguinteRaiz, indiceFinal).forEach(decisao -> {
            try {
                no.set(arvoreDecisao.depthFirstSerach(decisao.getNodeId()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            arvoreDecisao.preencherNoPelaDecisao(no.get(), decisao);
        });
        return arvoreDecisao;
    }
}
