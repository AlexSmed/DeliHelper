
package app.api;

import app.dao.BulletinDAO;
import app.dto.BulletinDTO;
import app.entities.Bulletin;
import io.javalin.Javalin;

public class BulletinApi {

    private final BulletinDAO bulletinDAO;

    public BulletinApi(BulletinDAO bulletinDAO) {
        this.bulletinDAO = bulletinDAO;
    }

    public void registerRoutes(Javalin app) {

        // GET all bulletins
        app.get("/bulletins", ctx -> {
            var bulletins = bulletinDAO.getAll()
                    .stream()
                    .map(BulletinDTO::new)
                    .toList();

            ctx.json(bulletins);
        });

        // GET bulletin by ID
        app.get("/bulletins/{id}", ctx -> {
            Long id = Long.parseLong(ctx.pathParam("id"));

            Bulletin bulletin = bulletinDAO.getById(id);

            if (bulletin == null) {
                ctx.status(404);
                return;
            }

            ctx.json(new BulletinDTO(bulletin));
        });

        // CREATE a new bulletin
        app.post("/bulletins", ctx -> {
            Bulletin bulletin = ctx.bodyAsClass(Bulletin.class);

            bulletinDAO.save(bulletin);

            ctx.status(201);
            ctx.json(new BulletinDTO(bulletin));
        });

        // UPDATE an existing bulletin
        app.put("/bulletins/{id}", ctx -> {
            Long id = Long.parseLong(ctx.pathParam("id"));

            Bulletin existingBulletin = bulletinDAO.getById(id);

            if (existingBulletin == null) {
                ctx.status(404);
                return;
            }

            Bulletin updatedBulletin =
                    ctx.bodyAsClass(Bulletin.class);

            existingBulletin.setTitle(updatedBulletin.getTitle());
            existingBulletin.setDescription(
                    updatedBulletin.getDescription());
            existingBulletin.setStore_id(
                    updatedBulletin.getStore_id());

            Bulletin savedBulletin =
                    bulletinDAO.update(existingBulletin);

            ctx.json(new BulletinDTO(savedBulletin));
        });

        // DELETE a bulletin
        app.delete("/bulletins/{id}", ctx -> {
            Long id = Long.parseLong(ctx.pathParam("id"));

            Bulletin bulletin = bulletinDAO.getById(id);

            if (bulletin == null) {
                ctx.status(404);
                return;
            }

            bulletinDAO.delete(id);

            ctx.status(204);
        });
    }
}
